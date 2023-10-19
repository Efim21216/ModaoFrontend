package ru.nsu.fit.modao.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
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
import ru.nsu.fit.modao.databinding.FragmentDataConfirmationBinding
import ru.nsu.fit.modao.databinding.PopUpWindowDataConfBinding
import ru.nsu.fit.modao.models.Expense
import ru.nsu.fit.modao.models.ExpenseListItem
import ru.nsu.fit.modao.models.LoadItems
import ru.nsu.fit.modao.utils.App
import ru.nsu.fit.modao.viewmodels.MainViewModel
import javax.inject.Inject

@AndroidEntryPoint
class DataConfirmationFragment : Fragment(), AdapterListener<ExpenseListItem> {
    private var _binding: FragmentDataConfirmationBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel: MainViewModel by viewModels()
    @Inject
    lateinit var app: App
    private val adapter = ExpensesAdapter()
    private val args by navArgs<DataConfirmationFragmentArgs>()
    private var organizer = false
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentDataConfirmationBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        class OnBack(enable: Boolean): OnBackPressedCallback(enable) {
            override fun handleOnBackPressed() {
                if (findNavController().currentBackStackEntry?.destination?.id == R.id.dataConfirmationFragment) {
                    if (findNavController().previousBackStackEntry?.destination?.id != R.id.groupInfoFragment) {

                        findNavController().navigate(DataConfirmationFragmentDirections
                            .actionDataConfirmationFragmentToGroupExpensesFragment(args.group))
                    } else {
                        findNavController().popBackStack()
                    }
                } else {
                    findNavController().popBackStack()
                }
            }
        }
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner,  OnBack(true))
        initObserver()
        mainViewModel.getGroupUnconfirmedExpenses(args.group.id!!)
        mainViewModel.getListOrganizers(args.group.id!!)
        adapter.attachListener(this)
        binding.recyclerUnconfirmed.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        binding.recyclerUnconfirmed.adapter = adapter
    }

    private fun initObserver() {
        mainViewModel.infoEvent.observe(viewLifecycleOwner) {item ->
            if (item == null) {
                return@observe
            }
            val builder = AlertDialog.Builder(context)
            val view = layoutInflater.inflate(R.layout.pop_up_window_data_conf, null)
            val alertBinding = PopUpWindowDataConfBinding.bind(view)
            builder.setView(alertBinding.root)
            alertBinding.cost.text = item.price?.toString()
            alertBinding.description.text = item.name
            alertBinding.whoCreated.text = item.usernameCreator
            if (!organizer || args.group.typeGroup == 1) {
                alertBinding.noButton.visibility = View.GONE
                alertBinding.yesButton.visibility = View.GONE
                alertBinding.textConfirm.visibility = View.GONE
            }
            val dialog = builder.create()
            alertBinding.noButton.setOnClickListener {
                val id = item.id
                mainViewModel.notConfirmEvent(args.group.id!!, id!!.toLong())
                dialog.dismiss()
            }
            alertBinding.yesButton.setOnClickListener {
                val id = item.id
                mainViewModel.confirmEvent( args.group.id!!, id!!.toLong())
                dialog.dismiss()
            }
            alertBinding.buttonDetails.setOnClickListener {
                dialog.dismiss()
                val action = DataConfirmationFragmentDirections
                    .actionDataConfirmationFragmentToSeeDetailsFragment(true, item)
                action.group = args.group
                findNavController().navigate(action)

            }
            dialog.show()
            mainViewModel.infoEvent.postValue(null)
        }
        mainViewModel.unconfirmedExpenses.observe(viewLifecycleOwner){
            val list: MutableList<ExpenseListItem> = it.toMutableList()
            adapter.setList(list.toTypedArray())
        }
        mainViewModel.organizers.observe(viewLifecycleOwner) {
            organizer = it.any { org -> org.id == app.userId }
        }
    }


    override fun onClickItem(item: ExpenseListItem) {
        when (item) {
            is LoadItems -> {
                if (item.isLoad) {
                    return
                }
                item.isLoad = true
                Toast.makeText(context, "Load new items", Toast.LENGTH_SHORT).show()
            }
            is Expense -> mainViewModel.getEventInfo(item.id!!, args.group.id!!)
        }
    }
}