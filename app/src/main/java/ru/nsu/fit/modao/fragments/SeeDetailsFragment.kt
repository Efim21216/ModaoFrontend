package ru.nsu.fit.modao.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import ru.nsu.fit.modao.adapter.ParticipantsEventAdapter
import ru.nsu.fit.modao.databinding.FragmentSeeDetailsBinding
import ru.nsu.fit.modao.models.ParticipantEvent
import ru.nsu.fit.modao.utils.App
import ru.nsu.fit.modao.utils.Constants.Companion.FAIL
import ru.nsu.fit.modao.utils.Constants.Companion.SUCCESS
import ru.nsu.fit.modao.viewmodels.MainViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@AndroidEntryPoint
class SeeDetailsFragment : Fragment() {
    private var _binding: FragmentSeeDetailsBinding? = null
    private val binding get() = _binding!!
    private val adapter = ParticipantsEventAdapter()
    private val args by navArgs<SeeDetailsFragmentArgs>()
    private val mainViewModel: MainViewModel by viewModels()
    @Inject
    lateinit var app: App
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSeeDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel.getListOrganizers(args.group?.id!!)
        setRecycler()
        initView()
        initObserver()
    }
    private fun setRecycler(){
        val list = args.expense.expenseDtoList?.map {
            ParticipantEvent(
                username = it.username,
                transferAmount = it.transferAmount
            )
        }
        if (list != null) {
            adapter.setList(list.toTypedArray())
        }
        binding.whoParticipatedRecycler.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.whoParticipatedRecycler.adapter = adapter
    }
    private fun initView(){
        if (!args.isConfirmation){
            binding.noButton2.visibility = View.GONE
            binding.yesButton2.visibility = View.GONE
            binding.textConfirm2.visibility = View.GONE
        }
        else {
            initButton()
        }
        binding.deleteButton.setOnClickListener {
            mainViewModel.deleteEvent(args.group?.id!!, args.expense.id!!, args.expense.name!!)
        }
        val time = LocalDateTime.parse(args.expense.time)
        val pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        binding.theCost.text = args.expense.price.toString()
        binding.theSpender.text = args.expense.usernamePaying
        binding.theCreator.text = args.expense.usernameCreator
        binding.theDate.text = time.format(pattern)
        binding.expenseName.text = args.expense.name
    }
    private fun initButton() {
        binding.noButton2.setOnClickListener {
            val id = args.expense.id
            mainViewModel.notConfirmEvent(args.group!!.id!!, id!!)
            findNavController().navigate(SeeDetailsFragmentDirections
                .actionSeeDetailsFragmentToDataConfirmationFragment(args.group!!))
        }
        binding.yesButton2.setOnClickListener {
            val id = args.expense.id
            mainViewModel.confirmEvent(args.group!!.id!!, id!!)
            findNavController().navigate(SeeDetailsFragmentDirections
                .actionSeeDetailsFragmentToDataConfirmationFragment(args.group!!))
        }

    }
    private fun initObserver() {
        mainViewModel.organizers.observe(viewLifecycleOwner) {
            val isOrganizer = it.any { org -> org.id == app.userId }
            if (isOrganizer && !args.isConfirmation) {
                binding.deleteButton.visibility = View.VISIBLE
            }
            if (!isOrganizer) {
                binding.noButton2.visibility = View.GONE
                binding.yesButton2.visibility = View.GONE
                binding.textConfirm2.visibility = View.GONE
            } else if (args.isConfirmation) {
                binding.noButton2.visibility = View.VISIBLE
                binding.yesButton2.visibility = View.VISIBLE
                binding.textConfirm2.visibility = View.VISIBLE
            }
        }
        mainViewModel.tipMessage.observe(viewLifecycleOwner) {
            when (it) {
                SUCCESS -> {
                    Toast.makeText(context, "Deleted", Toast.LENGTH_LONG).show()
                    findNavController().navigate(SeeDetailsFragmentDirections
                        .actionSeeDetailsFragmentToGroupExpensesFragment(args.group!!))
                }
                FAIL -> Toast.makeText(context, "Fail", Toast.LENGTH_LONG).show()
            }
        }
    }
}