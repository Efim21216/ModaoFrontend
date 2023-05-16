package ru.nsu.fit.modao.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import dagger.hilt.android.AndroidEntryPoint
import ru.nsu.fit.modao.adapter.AdapterListener
import ru.nsu.fit.modao.adapter.ExpensesAdapter
import ru.nsu.fit.modao.databinding.FragmentExpensesBinding
import ru.nsu.fit.modao.models.Currency
import ru.nsu.fit.modao.models.Expense
import ru.nsu.fit.modao.models.ExpenseListItem

@AndroidEntryPoint
class ExpensesFragment : Fragment(), AdapterListener<ExpenseListItem> {
    private var _binding: FragmentExpensesBinding? = null
    private val binding get() = _binding!!
    private val adapter: ExpensesAdapter = ExpensesAdapter()
    private val expenses: Array<ExpenseListItem> = arrayOf(
        Expense(name = "Nikita paid", currency = Currency.RUB, price = -100.0f),
        Expense(name = "Olga paid", currency = Currency.RUB, price = 200.0f),
        Expense(name = "Efim paid", currency = Currency.RUB, price = -150.0f),
        Expense(name = "Peter paid", currency = Currency.RUB, price = 99.9f)
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExpensesBinding.inflate(inflater, container, false)
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
        binding.expensesRecycler.layoutManager = LinearLayoutManager(this.context, VERTICAL, false)
        binding.expensesRecycler.adapter = adapter
    }

    override fun onClickItem(item: ExpenseListItem) {
        TODO("Not yet implemented")
    }
}