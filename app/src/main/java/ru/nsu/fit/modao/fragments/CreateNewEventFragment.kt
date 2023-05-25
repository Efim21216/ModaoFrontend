package ru.nsu.fit.modao.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import ru.nsu.fit.modao.databinding.FragmentCreateNewEventBinding
import ru.nsu.fit.modao.models.CreationExpense
import ru.nsu.fit.modao.models.ParticipantEvent
import ru.nsu.fit.modao.models.User
import ru.nsu.fit.modao.utils.App
import ru.nsu.fit.modao.viewmodels.CreateExpenseViewModel
import ru.nsu.fit.modao.viewmodels.MainViewModel
import javax.inject.Inject

@AndroidEntryPoint
class CreateNewEventFragment : Fragment() {
    private var _binding: FragmentCreateNewEventBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var app: App
    private val createExpenseViewModel: CreateExpenseViewModel by activityViewModels()
    private val mainViewModel: MainViewModel by viewModels()
    private val args by navArgs<CreateNewEventFragmentArgs>()
    private var lastEvent: Long? = null
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

        mainViewModel.getUsersInGroup(args.group.id!!)
        createExpenseViewModel.payFor = false

        initObserver()
        initButton()

    }

    private fun initButton() {
        binding.grayNewTransfer.setOnClickListener {
            changeMode(View.VISIBLE, View.GONE)
        }
        binding.grayNewExpense.setOnClickListener {
            changeMode(View.GONE, View.VISIBLE)
        }


        binding.buttonMoreOptions.setOnClickListener {
            createExpenseViewModel.payFor = false
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

            findNavController().navigate(
                CreateNewEventFragmentDirections
                    .actionCreateAnExpenseFragmentToCreateExpenseFragment(
                        CreationExpense(
                            args.group,
                            cost,
                            description
                        )
                    )
            )
        }
        binding.buttonAll.setOnClickListener {
            createExpenseViewModel.participants.value?.forEach { user -> user.selected = true }
            createExpenseViewModel.payFor = false
            createExpenseViewModel.participants.value = createExpenseViewModel.users
            Toast.makeText(context, "All members are selected", Toast.LENGTH_SHORT).show()
        }

        binding.buttonTwo.setOnClickListener {
            createExpenseViewModel.payFor = false
            findNavController().navigate(
                CreateNewEventFragmentDirections
                    .actionCreateAnExpenseFragmentToSelectSecondParticipantFragment(args.group)
            )
        }
        binding.selectParticipant.setOnClickListener {
            createExpenseViewModel.payFor = false
            findNavController().navigate(
                CreateNewEventFragmentDirections
                    .actionCreateAnExpenseFragmentToSelectSecondParticipantFragment(args.group)
            )
        }
        binding.buttonPayFor.setOnClickListener {
            createExpenseViewModel.payFor = true
            findNavController().navigate(
                CreateNewEventFragmentDirections
                    .actionCreateAnExpenseFragmentToSelectSecondParticipantFragment(args.group)
            )
        }
        binding.buttonFinish.setOnClickListener {
            var type = 0
            if (!binding.newExpenseButton.isVisible) {
                type = 1
            }
            createExpenseViewModel.createExpense(
                binding.enterDescription.text.toString(),
                binding.enterCost.text.toString(),
                args.group.id!!, type
            )
        }
    }

    private fun initObserver() {
        mainViewModel.usersInGroup.observe(viewLifecycleOwner) {
            val array = it.map { user ->
                createListParticipant(user)
            }.toTypedArray()
            createExpenseViewModel.users = array
            createExpenseViewModel.participants.value = array.clone()
        }
        createExpenseViewModel.message.observe(viewLifecycleOwner) {
            if (it != null) {
                val builder = AlertDialog.Builder(context)
                builder.setMessage(it)
                builder.setPositiveButton("OK") { _, _ -> }
                builder.create().show()
                createExpenseViewModel.message.postValue(null)
            } else Log.d("MyTag", "null")
        }

        createExpenseViewModel.eventId.observe(viewLifecycleOwner) {
            if (lastEvent === it) {
                return@observe
            }
            createExpenseViewModel.eventId.value = lastEvent
            findNavController().navigate(
                CreateNewEventFragmentDirections
                    .actionCreateAnExpenseFragmentToDataConfirmationFragment(args.group)
            )
        }
    }

    private fun changeMode(transfer: Int, expense: Int) {
        binding.grayNewExpense.visibility = transfer
        binding.newExpenseButton.visibility = expense
        binding.grayNewTransfer.visibility = expense
        binding.newTransferButton.visibility = transfer
        binding.selectParticipant.visibility = transfer
        binding.buttonMoreOptions.visibility = expense
        binding.buttonTwo.visibility = expense
        binding.buttonAll.visibility = expense
        binding.buttonPayFor.visibility = expense
    }

    private fun createListParticipant(user: User): ParticipantEvent {
        return if (user.id == app.userId) {
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
    }
}