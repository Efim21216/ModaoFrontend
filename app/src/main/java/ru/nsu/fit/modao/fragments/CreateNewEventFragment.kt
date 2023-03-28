package ru.nsu.fit.modao.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ru.nsu.fit.modao.databinding.FragmentCreateNewEventBinding
import ru.nsu.fit.modao.models.CreationExpense
import ru.nsu.fit.modao.models.ParticipantEvent
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

        binding.buttonFinish.setOnClickListener {

            createExpenseViewModel.createExpense(
                binding.enterDescription.text.toString(),
                binding.enterCost.text.toString(),
                args.group.id!!
            )
        }

        mainViewModel.getUsersInGroup(args.group.id!!)

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
            var cost: Float = 0f
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
    }

    private fun initService() {
        app = activity?.application as App
        val repository = Repository(app!!)
        val viewModelFactory = RepositoryViewModelFactory(repository)
        createExpenseViewModel =
            ViewModelProvider(this, viewModelFactory)[CreateExpenseViewModel::class.java]
        mainViewModel =
            ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
    }
}