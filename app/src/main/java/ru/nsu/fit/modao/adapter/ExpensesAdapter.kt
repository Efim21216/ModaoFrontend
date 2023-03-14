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

class ExpensesAdapter: RecyclerView.Adapter<ExpensesAdapter.ExpensesHolder>() {
    private lateinit var expensesList: Array<Expense>
    private lateinit var listener: AdapterListener<Expense>
    fun attachListener(listener: AdapterListener<Expense>){
        this.listener = listener
    }
    class ExpensesHolder(item: View): RecyclerView.ViewHolder(item){
        val binding = ExpenseItemBinding.bind(item)
        fun bind(expense: Expense, listener: AdapterListener<Expense>) = with(binding){
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpensesHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.expense_item, parent, false)
        return ExpensesHolder(view)
    }

    override fun onBindViewHolder(holder: ExpensesHolder, position: Int) {
        holder.bind(expensesList[position], listener)
    }

    override fun getItemCount(): Int {
        return expensesList.size
    }
    fun setList(list: Array<Expense>){
        expensesList = list
        notifyDataSetChanged()
    }
}