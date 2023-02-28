package ru.nsu.fit.modao.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.nsu.fit.modao.adapter.ExpensesAdapter
import ru.nsu.fit.modao.databinding.FragmentGroupExpensesBinding
import ru.nsu.fit.modao.models.Expense
import ru.nsu.fit.modao.repository.Repository
import ru.nsu.fit.modao.utils.App
import ru.nsu.fit.modao.viewmodels.MainViewModel
import ru.nsu.fit.modao.viewmodels.RepositoryViewModelFactory

class GroupExpensesFragment: Fragment() {
    private var _binding: FragmentGroupExpensesBinding? = null
    private val binding get() = _binding!!
    private val adapter = ExpensesAdapter()
    private val args by navArgs<GroupExpensesFragmentArgs>()
    private lateinit var mainViewModel: MainViewModel
    private lateinit var app: App
    private var expenses: Array<Expense> = arrayOf()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentGroupExpensesBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        app = activity?.application as App
        val repository = Repository(app)
        val viewModelFactory = RepositoryViewModelFactory(repository)
        mainViewModel = ViewModelProvider(requireActivity(), viewModelFactory)[MainViewModel::class.java]
        adapter.setList(expenses)
        mainViewModel.expenses.observe(viewLifecycleOwner){
            adapter.setList(it)
        }
        binding.expensesRecycler.layoutManager = LinearLayoutManager(this.context, RecyclerView.VERTICAL, false)
        binding.expensesRecycler.adapter = adapter
        binding.buttonAddExpense.setOnClickListener {
            findNavController().navigate(GroupExpensesFragmentDirections
                .actionGroupExpensesFragmentToCreateExpenseFragment(args.group))
        }
        mainViewModel.getGroupExpenses(args.group.id!!)
    }
}