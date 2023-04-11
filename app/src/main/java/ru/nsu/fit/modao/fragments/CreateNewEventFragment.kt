package ru.nsu.fit.modao.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ru.nsu.fit.modao.databinding.FragmentCreateNewEventBinding
import ru.nsu.fit.modao.models.CreationExpense
import ru.nsu.fit.modao.models.CreationExpenseViaBottom
import ru.nsu.fit.modao.models.ParticipantEvent
import ru.nsu.fit.modao.models.User
import ru.nsu.fit.modao.repository.Repository
import ru.nsu.fit.modao.utils.App
import ru.nsu.fit.modao.viewmodels.CreateExpenseViewModel
import ru.nsu.fit.modao.viewmodels.MainViewModel
import ru.nsu.fit.modao.viewmodels.RepositoryViewModelFactory
class CreateNewEventFragment : Fragment() {
    private var _binding: FragmentCreateNewEventBinding? = null
    private val binding get() = _binding!!
    private var app: App? = null
    private lateinit var createExpenseViewModel: CreateExpenseViewModel
    private lateinit var mainViewModel: MainViewModel
    private val args by navArgs<CreateNewEventFragmentArgs>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateNewEventBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initService()
        mainViewModel.getUsersInGroup(args.group.id!!)

        mainViewModel.usersInGroup.observe(viewLifecycleOwner) {
            createExpenseViewModel.participants.value = it.map { user ->
                createListParticipant(user)
            }.toTypedArray()
        }
        createExpenseViewModel.message.observe(viewLifecycleOwner) {
            val builder = AlertDialog.Builder(context)
            builder.setMessage(it)
            builder.setPositiveButton("OK") { _, _ -> }
            builder.create().show()
        }

        createExpenseViewModel.eventId.observe(viewLifecycleOwner) {
            findNavController().navigate(CreateNewEventFragmentDirections
                .actionCreateAnExpenseFragmentToGroupExpensesFragment(args.group))
        }

        binding.buttonMoreOptions.setOnClickListener {
            val description = binding.enterDescription.text.toString()
            val builder = AlertDialog.Builder(context)
            builder.setPositiveButton("OK") { _, _ -> }
            if (description == "") {
                builder.setTitle("Enter description")
                builder.create().show()
                return@setOnClickListener
            }
            val cost: Float
            try {
                 cost = binding.enterCost.text.toString().toFloat()
            } catch (e: NumberFormatException) {
                builder.setTitle("Enter cost")
                builder.create().show()
                return@setOnClickListener
            }

            findNavController().navigate(CreateNewEventFragmentDirections
                .actionCreateAnExpenseFragmentToCreateExpenseFragment(CreationExpense(args.group, cost, description)))
        }
        binding.buttonAll.setOnClickListener {
            createExpenseViewModel.participants.value?.forEach { user -> user.selected = true }
            Toast.makeText(context, "All members are selected", Toast.LENGTH_SHORT).show()
        }
        binding.grayNewTransfer.setOnClickListener {
            changeMode(View.VISIBLE, View.GONE)
        }
        binding.grayNewExpense.setOnClickListener {
            changeMode(View.GONE, View.VISIBLE)
        }
        binding.buttonTwo.setOnClickListener {
            findNavController().navigate(CreateNewEventFragmentDirections
                .actionCreateAnExpenseFragmentToSelectSecondParticipantFragment(args.group,
                    CreationExpenseViaBottom(isEvent = true,
                        cost = binding.enterCost.text.toString(),
                        description = binding.enterDescription.text.toString())))
        }
        binding.selectParticipant.setOnClickListener {
            findNavController().navigate(CreateNewEventFragmentDirections
                .actionCreateAnExpenseFragmentToSelectSecondParticipantFragment(args.group,
                    CreationExpenseViaBottom(isEvent = false,
                        cost = binding.enterCost.text.toString(),
                        description = binding.enterDescription.text.toString())))
        }

        binding.buttonFinish.setOnClickListener {
            var type = 0
            if (!binding.newExpenseButton.isVisible) {
                if (args.infoExpense?.second == null) {
                    createExpenseViewModel.message.value = "Select the participant"
                    return@setOnClickListener
                }
                type = 1
            }
            createExpenseViewModel.createExpense(
                binding.enterDescription.text.toString(),
                binding.enterCost.text.toString(),
                args.group.id!!, type
            )
        }
    }

    private fun changeMode(transfer: Int, expense: Int){
        binding.grayNewExpense.visibility = transfer
        binding.newExpenseButton.visibility = expense
        binding.grayNewTransfer.visibility = expense
        binding.newTransferButton.visibility = transfer
        binding.selectParticipant.visibility = transfer
        binding.buttonMoreOptions.visibility = expense
        binding.buttonTwo.visibility = expense
        binding.buttonAll.visibility = expense
    }
    private fun initService() {
        app = activity?.application as App
        val repository = Repository(app!!)
        val viewModelFactory = RepositoryViewModelFactory(repository)
        createExpenseViewModel =
            ViewModelProvider(this, viewModelFactory)[CreateExpenseViewModel::class.java]
        mainViewModel =
            ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        if (args.infoExpense == null) {
            return
        }
        if (args.infoExpense?.cost != ""){
            binding.enterCost.setText(args.infoExpense?.cost)
        }
        if (args.infoExpense?.description != ""){
            binding.enterDescription.setText(args.infoExpense?.description)
        }
        if (!args.infoExpense!!.isEvent){
            changeMode(View.VISIBLE, View.GONE)
        }

    }
    private fun createListParticipant(user: User): ParticipantEvent{
        if (user.id == app!!.userId) {
            return ParticipantEvent(
                username = user.username,
                id = user.id,
                selected = true,
                isSponsor = true
            )
        } else {
            var select = true
            if (args.infoExpense?.second != null && args.infoExpense?.second?.id != user.id){
                select = false
            }
            return ParticipantEvent(
                username = user.username,
                id = user.id,
                selected = select,
                isSponsor = false
            )
        }
    }
}