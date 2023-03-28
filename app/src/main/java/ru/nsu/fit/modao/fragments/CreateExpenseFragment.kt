package ru.nsu.fit.modao.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.nsu.fit.modao.R
import ru.nsu.fit.modao.adapter.AdapterListener
import ru.nsu.fit.modao.adapter.MenuAdapter
import ru.nsu.fit.modao.databinding.FragmentCreateExpenseBinding
import ru.nsu.fit.modao.models.ParticipantEvent
import ru.nsu.fit.modao.repository.Repository
import ru.nsu.fit.modao.utils.App
import ru.nsu.fit.modao.viewmodels.CreateExpenseViewModel
import ru.nsu.fit.modao.viewmodels.MainViewModel
import ru.nsu.fit.modao.viewmodels.RepositoryViewModelFactory

class CreateExpenseFragment : Fragment(), AdapterListener<String> {
    private var _binding: FragmentCreateExpenseBinding? = null
    private val binding get() = _binding!!
    private var menuList = listOf("Who spent", "Participants", "Coefficient", "More option")
    private val adapter = MenuAdapter()
    private var app: App? = null
    private val args by navArgs<CreateExpenseFragmentArgs>()
    private lateinit var mainViewModel: MainViewModel
    private lateinit var createExpenseViewModel: CreateExpenseViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateExpenseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerMenu.layoutManager =
            LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        adapter.attachListener(this)
        adapter.setList(menuList)
        binding.recyclerMenu.adapter = adapter

        app = activity?.application as App
        val repository = Repository(app!!)
        val viewModelFactory = RepositoryViewModelFactory(repository)
        createExpenseViewModel =
            ViewModelProvider(this, viewModelFactory)[CreateExpenseViewModel::class.java]
        mainViewModel =
            ViewModelProvider(requireActivity(), viewModelFactory)[MainViewModel::class.java]

        mainViewModel.getUsersInGroup(args.dataExpense.group.id!!)

        createExpenseViewModel.eventId.observe(viewLifecycleOwner) {
            findNavController().navigate(
                CreateExpenseFragmentDirections
                    .actionCreateExpenseFragmentToGroupExpensesFragment(args.dataExpense.group)
            )
        }
        mainViewModel.usersInGroup.observe(viewLifecycleOwner) {
            createExpenseViewModel.participants.value = it.map { user ->
                if (user.id == app!!.userId) {
                    ParticipantEvent(
                        username = user.username,
                        id = user.id,
                        selected = true,
                        isSponsor = true
                    )
                } else {
                    ParticipantEvent(
                        username = user.username,
                        id = user.id,
                        selected = true,
                        isSponsor = false
                    )
                }
            }.toTypedArray()
        }
        createExpenseViewModel.message.observe(viewLifecycleOwner) {
            val builder = AlertDialog.Builder(context)
            builder.setMessage(it)
            builder.setPositiveButton("OK") { _, _ -> }
            builder.create().show()
        }
        binding.buttonFinish.setOnClickListener {
            createExpenseViewModel.createExpense(
                args.dataExpense.description,
                args.dataExpense.cost.toString(),
                args.dataExpense.group.id!!
            )
        }
    }

    override fun onClickItem(item: String) {
        when (item) {
            menuList[0] -> activity?.findNavController(R.id.innerFragment)
                ?.navigate(R.id.enterCostFragment)
            menuList[1] -> activity?.findNavController(R.id.innerFragment)
                ?.navigate(R.id.selectParticipantsFragment)
            menuList[2] -> activity?.findNavController(R.id.innerFragment)
                ?.navigate(R.id.enterCoefficientsFragment)
        }
    }
}