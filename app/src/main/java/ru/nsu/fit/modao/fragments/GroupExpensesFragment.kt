package ru.nsu.fit.modao.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import ru.nsu.fit.modao.R
import ru.nsu.fit.modao.adapter.AdapterListener
import ru.nsu.fit.modao.adapter.ExpensesAdapter
import ru.nsu.fit.modao.databinding.FilterExpensesBinding
import ru.nsu.fit.modao.databinding.FragmentGroupExpensesBinding
import ru.nsu.fit.modao.databinding.PopUpWindowDataConfBinding
import ru.nsu.fit.modao.models.Expense
import ru.nsu.fit.modao.models.ExpenseListItem
import ru.nsu.fit.modao.models.LoadItems
import ru.nsu.fit.modao.utils.Constants.Companion.PAGE_SIZE
import ru.nsu.fit.modao.viewmodels.MainViewModel
import java.text.DateFormat
import java.util.*

@AndroidEntryPoint
class GroupExpensesFragment : Fragment(), AdapterListener<ExpenseListItem> {
    private var _binding: FragmentGroupExpensesBinding? = null
    private val binding get() = _binding!!
    private val adapter = ExpensesAdapter()
    private val args by navArgs<GroupExpensesFragmentArgs>()
    private val mainViewModel: MainViewModel by viewModels()
    private var showEvent = true
    private var showTransfer = true
    private var showOnlyMy = false
    private var lastEvent: Expense? = null
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

        setRecycler()
        setButtonOnClick()
        setPopupWindow()
        mainViewModel.getGroupExpenses(args.group.id!!, showOnlyMy.intValue, 2)
        setObserver()

    }
    private fun setObserver() {

        mainViewModel.expenses.observe(viewLifecycleOwner) {
            val list: MutableList<ExpenseListItem> = it.toMutableList()
            if (it.size > PAGE_SIZE) {
                list.add(PAGE_SIZE - 5, LoadItems(isLoad = false))
            }
            adapter.setList(list.toTypedArray())
        }

        mainViewModel.infoEvent.observe(viewLifecycleOwner) {
            if (lastEvent === it) {
                return@observe
            }
            lastEvent = it
            val builder = AlertDialog.Builder(context)
            val bindingAlert = PopUpWindowDataConfBinding
                .bind(layoutInflater.inflate(R.layout.pop_up_window_data_conf, null))
            bindingAlert.description.text = it.name
            bindingAlert.cost.text = it.price?.toString()
            bindingAlert.whoCreated.text = it.usernameCreator
            bindingAlert.yesButton.visibility = View.GONE
            bindingAlert.noButton.visibility = View.GONE
            bindingAlert.textConfirm.visibility = View.GONE
            builder.setView(bindingAlert.root)
            val dialog = builder.create()
            bindingAlert.buttonDetails.setOnClickListener {_ ->
                dialog.dismiss()
                findNavController().navigate(GroupExpensesFragmentDirections
                    .actionGroupExpensesFragmentToSeeDetailsFragment(false, it))
            }

            dialog.show()
        }
    }
    private fun setButtonOnClick() {
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
        binding.filterIcon.setOnClickListener {
            window.showAsDropDown(binding.filterIcon, -250, 0)
        }

        binding.buttonFilterByDate.setOnClickListener {
            showDatePicker()
        }
    }
    private fun showDatePicker(){
        val dialog = MaterialDatePicker.Builder.dateRangePicker()
            .setTheme(R.style.MaterialCalendarTheme)
            .setPositiveButtonText("Save")
            .setNegativeButtonText("Cancel")
            .build()
        dialog.addOnPositiveButtonClickListener {
            val date1 = Date(it.first)
            val date2 = Date(it.second)
            Toast.makeText(context, "${DateFormat.getDateInstance().format(date1)} " +
                    "- ${DateFormat.getDateInstance().format(date2)}",
                Toast.LENGTH_LONG).show()
        }
        dialog.show(childFragmentManager, "MyTag")
    }
    private fun setRecycler(){
        adapter.attachListener(this)
        binding.expensesRecycler.layoutManager =
            LinearLayoutManager(this.context, RecyclerView.VERTICAL, false)
        binding.expensesRecycler.adapter = adapter
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
    override fun onClickItem(item: ExpenseListItem) {
        when (item) {
            is Expense -> mainViewModel.getEventInfo(item.id!!, args.group.id!!)
            is LoadItems -> {
                if (item.isLoad) {
                    return
                }
                item.isLoad = true
            }
        }

    }
}