package ru.nsu.fit.modao.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.nsu.fit.modao.R
import ru.nsu.fit.modao.adapter.AdapterListener
import ru.nsu.fit.modao.adapter.ExpensesAdapter
import ru.nsu.fit.modao.databinding.FragmentDataConfirmationBinding
import ru.nsu.fit.modao.databinding.PopUpWindowDataConfBinding
import ru.nsu.fit.modao.models.Expense
import ru.nsu.fit.modao.repository.Repository
import ru.nsu.fit.modao.utils.App
import ru.nsu.fit.modao.viewmodels.MainViewModel
import ru.nsu.fit.modao.viewmodels.RepositoryViewModelFactory


class DataConfirmationFragment : Fragment(), AdapterListener<Expense> {
    private var _binding: FragmentDataConfirmationBinding? = null
    private val binding get() = _binding!!
    private lateinit var mainViewModel: MainViewModel
    private lateinit var  app: App
    private val adapter = ExpensesAdapter()
    private val args by navArgs<DataConfirmationFragmentArgs>()
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

        app = requireActivity().application as App
        val repository = Repository(app)
        val viewModelFactory = RepositoryViewModelFactory(repository)
        mainViewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]

        mainViewModel.unconfirmedExpenses.observe(viewLifecycleOwner){
            adapter.setList(it)
        }
        mainViewModel.getGroupUnconfirmedExpenses(args.group.id!!)
        val list: Array<Expense> = mainViewModel.unconfirmedExpenses.value ?: arrayOf()
        adapter.setList(list)
        adapter.attachListener(this)
        binding.recyclerUnconfirmed.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        binding.recyclerUnconfirmed.adapter = adapter
    }

    override fun onClickItem(item: Expense) {
        val builder = AlertDialog.Builder(context)
        val view = layoutInflater.inflate(R.layout.pop_up_window_data_conf, null)
        val alertBinding = PopUpWindowDataConfBinding.bind(view)
        builder.setView(alertBinding.root)
        alertBinding.cost.text = item.price?.toString()
        alertBinding.description.text = item.name
        alertBinding.whoCreated.text = item.usernameCreator
        val dialog = builder.create()
        alertBinding.noButton.setOnClickListener {
            val id = item.id
            mainViewModel.notConfirmEvent(id!!.toLong(), args.group.id!!)
            dialog.dismiss()
        }
        alertBinding.yesButton.setOnClickListener {
            val id = item.id
            mainViewModel.confirmEvent( args.group.id!!, id!!.toLong())
            dialog.dismiss()
        }
        dialog.show()
    }
}