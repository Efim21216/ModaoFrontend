package ru.nsu.fit.modao.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import ru.nsu.fit.modao.R
import ru.nsu.fit.modao.adapter.AdapterListener
import ru.nsu.fit.modao.adapter.ExpensesAdapter
import ru.nsu.fit.modao.databinding.FilterExpensesBinding
import ru.nsu.fit.modao.databinding.FragmentGroupExpensesBinding
import ru.nsu.fit.modao.models.Expense
import ru.nsu.fit.modao.viewmodels.MainViewModel
@AndroidEntryPoint
class GroupExpensesFragment : Fragment(), AdapterListener<Expense> {
    private var _binding: FragmentGroupExpensesBinding? = null
    private val binding get() = _binding!!
    private val adapter = ExpensesAdapter()
    private val args by navArgs<GroupExpensesFragmentArgs>()
    private val mainViewModel: MainViewModel by viewModels()
    private var expenses: Array<Expense> = arrayOf()
    private var showEvent = true
    private var showTransfer = true
    private var showOnlyMy = false
    private lateinit var window: PopupWindow
    private lateinit var bindingPopupWindow: FilterExpensesBinding
    private val Boolean.intValue
        get() = if (this) 1 else 0
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGroupExpensesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter.setList(expenses)
        adapter.attachListener(this)
        mainViewModel.expenses.observe(viewLifecycleOwner) {
            adapter.setList(it)
        }
        binding.expensesRecycler.layoutManager =
            LinearLayoutManager(this.context, RecyclerView.VERTICAL, false)
        binding.expensesRecycler.adapter = adapter

        binding.buttonAddEvent.setOnClickListener {
            findNavController().navigate(
                GroupExpensesFragmentDirections
                    .actionGroupExpensesFragmentToCreateAnExpenseFragment(args.group)
            )
        }

        binding.buttonMyDebt.setOnClickListener {
            findNavController().navigate(
                GroupExpensesFragmentDirections
                    .actionGroupExpensesFragmentToUserExpensesInGroupFragment(args.group)
            )
        }
        setPopupWindow()
        mainViewModel.getGroupExpenses(args.group.id!!, showOnlyMy.intValue, 2)

        mainViewModel.infoEvent.observe(viewLifecycleOwner) {
            val builder = AlertDialog.Builder(context)
            builder.setTitle(it.name)
            val info: StringBuilder = java.lang.StringBuilder()
            it.expenseDtoList?.forEach { participantEvent ->
                info.append(
                    participantEvent.username + ": "
                            + participantEvent.coefficient?.toString()
                            + "\n"
                )
            }
            builder.setMessage(info.toString())
            builder.setPositiveButton("OK") { _, _ -> }
            builder.create().show()
        }
        binding.filterIcon.setOnClickListener {
            window.showAsDropDown(binding.filterIcon, -250, 0)
        }

    }

    private fun setPopupWindow(){
        window = PopupWindow(context)
        val view = LayoutInflater.from(context).inflate(R.layout.filter_expenses, binding.root, false)
        bindingPopupWindow = FilterExpensesBinding.bind(view)
        window.contentView = bindingPopupWindow.root
        window.isFocusable = true
        bindingPopupWindow.event.isChecked = true
        bindingPopupWindow.transfer.isChecked = true
        setButton()
    }
    private fun setButton(){
        bindingPopupWindow.transfer.setOnClickListener {
            if (showTransfer){
                if (bindingPopupWindow.event.isChecked){
                    showTransfer = false
                    bindingPopupWindow.transfer.isChecked = false
                }
            } else {
                showTransfer = true
                bindingPopupWindow.transfer.isChecked = true
            }
            getGroupExpenses()
        }

        bindingPopupWindow.event.setOnClickListener {
            if (showEvent) {
                if (bindingPopupWindow.transfer.isChecked) {
                    bindingPopupWindow.event.isChecked = false
                    showEvent = false
                }
            } else {
                bindingPopupWindow.event.isChecked = true
                showEvent = true
            }
            getGroupExpenses()
        }
        bindingPopupWindow.onlyMy.setOnClickListener {
            showOnlyMy = !showOnlyMy
            bindingPopupWindow.onlyMy.isChecked = showOnlyMy
            getGroupExpenses()
        }
    }
    private fun getGroupExpenses(){
        if (bindingPopupWindow.transfer.isChecked){
            if (bindingPopupWindow.event.isChecked) {
                mainViewModel.getGroupExpenses(args.group.id!!, showOnlyMy.intValue, 2)
            } else {
                mainViewModel.getGroupExpenses(args.group.id!!, showOnlyMy.intValue, 1)
            }
        } else {
            if (bindingPopupWindow.event.isChecked) {
                mainViewModel.getGroupExpenses(args.group.id!!, showOnlyMy.intValue, 0)
            }
        }
    }
    override fun onClickItem(item: Expense) {
        mainViewModel.getEventInfo(item.id!!, args.group.id!!)
    }
}