package ru.nsu.fit.modao.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import ru.nsu.fit.modao.adapter.ExpensesAdapter
import ru.nsu.fit.modao.databinding.FragmentExpensesBinding
import ru.nsu.fit.modao.models.Currency
import ru.nsu.fit.modao.models.Expense

class ExpensesFragment : Fragment() {
    private var _binding: FragmentExpensesBinding? = null
    private val binding get() = _binding!!
    private val adapter: ExpensesAdapter = ExpensesAdapter()
    private val expenses: ArrayList<Expense> = ArrayList()
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

        expenses.add(Expense("Nikita paid", "Nikita Paid", Currency.RUB, -100.0))
        expenses.add(Expense("Nikita paid", "Nikita Paid", Currency.RUB, 200.0))
        expenses.add(Expense("You paid", "You paid", Currency.RUB, 300.0))
        expenses.add(Expense("Olga paid", "Olga Paid", Currency.RUB, -230.0))


        adapter.setList(expenses)
        binding.expensesRecycler.layoutManager = LinearLayoutManager(this.context, VERTICAL, false)
        binding.expensesRecycler.adapter = adapter
    }
}