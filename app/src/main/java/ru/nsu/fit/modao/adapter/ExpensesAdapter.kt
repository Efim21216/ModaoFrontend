package ru.nsu.fit.modao.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.nsu.fit.modao.R
import ru.nsu.fit.modao.databinding.ExpenseItemBinding
import ru.nsu.fit.modao.models.Currency
import ru.nsu.fit.modao.models.Expense
import ru.nsu.fit.modao.models.ExpenseListItem
import ru.nsu.fit.modao.models.LoadItems

class ExpensesAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var expensesList: MutableList<ExpenseListItem> = mutableListOf()
    private lateinit var listener: AdapterListener<ExpenseListItem>
    companion object {
        const val EXPENSE = 0
        const val LOAD = 1
    }
    fun attachListener(listener: AdapterListener<ExpenseListItem>){
        this.listener = listener
    }
    fun setList(list: Array<ExpenseListItem>){
        expensesList = list.toMutableList()
        notifyDataSetChanged()
    }
    fun addItems(list: Array<ExpenseListItem>) {
        val size = expensesList.size
        expensesList.addAll(list)
        notifyItemRangeChanged(size, list.size)
    }
    class LoadHolder(item: View): RecyclerView.ViewHolder(item) {
        fun bind(load: LoadItems, listener: AdapterListener<ExpenseListItem>) {
            listener.onClickItem(load)
        }
    }
    class ExpensesHolder(item: View): RecyclerView.ViewHolder(item){
        val binding = ExpenseItemBinding.bind(item)
        fun bind(expense: Expense, listener: AdapterListener<ExpenseListItem>) = with(binding){
            shortInfo.text = expense.name
            amountExpense.text = expense.price.toString()
            if (expense.price!! > 0){
                if (expense.currency == Currency.RUB){
                    currencyImage.setImageResource(R.drawable.ic_profit_rub)
                }
            } else {
                if (expense.currency == Currency.RUB) {
                    currencyImage.setImageResource(R.drawable.ic_loss_rub)
                    amountExpense.setTextColor(Color.parseColor("#D46E6E"))
                }
            }
            root.setOnClickListener {
                listener.onClickItem(expense)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            EXPENSE -> ExpensesHolder(inflater.inflate(R.layout.expense_item, parent, false))
            LOAD -> LoadHolder(inflater.inflate(R.layout.empty_item, parent, false))
            else -> throw IllegalArgumentException("Illegal view type: $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ExpensesHolder -> holder.bind(expensesList[position] as Expense, listener)
            is LoadHolder -> holder.bind(expensesList[position] as LoadItems, listener)
        }

    }

    override fun getItemViewType(position: Int): Int {
        return when (expensesList[position]) {
            is Expense -> EXPENSE
            is LoadItems -> LOAD
        }
    }

    override fun getItemCount(): Int {
        return expensesList.size
    }

}