package ru.nsu.fit.modao.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.nsu.fit.modao.R
import ru.nsu.fit.modao.databinding.CounterEventItemBinding
import ru.nsu.fit.modao.databinding.DeletedEventItemBinding
import ru.nsu.fit.modao.databinding.ExpenseItemBinding
import ru.nsu.fit.modao.databinding.TransferItemBinding
import ru.nsu.fit.modao.models.Currency
import ru.nsu.fit.modao.models.Expense
import ru.nsu.fit.modao.models.ExpenseListItem
import ru.nsu.fit.modao.models.LoadItems

class ExpensesAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var expensesList: MutableList<ExpenseListItem> = mutableListOf()
    private lateinit var listener: AdapterListener<ExpenseListItem>
    companion object {
        const val EXPENSE = 0
        const val TRANSFER = 1
        const val DELETED = 2
        const val DELETING = 3
        const val LOAD = 4
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            EXPENSE -> ExpensesHolder(inflater.inflate(R.layout.expense_item, parent, false))
            TRANSFER -> TransferHolder(inflater.inflate(R.layout.transfer_item, parent, false))
            DELETED -> DeletedEventHolder(inflater.inflate(R.layout.deleted_event_item, parent, false))
            DELETING -> CounterEventHolder(inflater.inflate(R.layout.counter_event_item, parent, false))
            LOAD -> LoadHolder(inflater.inflate(R.layout.empty_item, parent, false))
            else -> throw IllegalArgumentException("Illegal view type: $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ExpensesHolder -> holder.bind(expensesList[position] as Expense, listener)
            is TransferHolder -> holder.bind(expensesList[position] as Expense, listener)
            is DeletedEventHolder -> holder.bind(expensesList[position] as Expense, listener)
            is CounterEventHolder -> holder.bind(expensesList[position] as Expense, listener)
            is LoadHolder -> holder.bind(expensesList[position] as LoadItems, listener)
        }

    }

    override fun getItemViewType(position: Int): Int {
        return when (expensesList[position]) {
            is Expense -> {
                val expense = expensesList[position] as Expense

                when (expense.status) {
                    0 -> if (expense.type == 0) EXPENSE else TRANSFER
                    1 -> if (expense.type == 0) EXPENSE else TRANSFER
                    -2 -> DELETING
                    -1 -> DELETED
                    else -> throw IllegalArgumentException("status ${expense.status}")
                }
            }
            is LoadItems -> LOAD
        }
    }

    override fun getItemCount(): Int {
        return expensesList.size
    }
    class LoadHolder(item: View): RecyclerView.ViewHolder(item) {
        fun bind(load: LoadItems, listener: AdapterListener<ExpenseListItem>) {
            listener.onClickItem(load)
        }
    }
    class DeletedEventHolder(item: View): RecyclerView.ViewHolder(item) {
        val binding = DeletedEventItemBinding.bind(item)
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
    class CounterEventHolder(item: View): RecyclerView.ViewHolder(item) {
        val binding = CounterEventItemBinding.bind(item)
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
    class TransferHolder(item: View): RecyclerView.ViewHolder(item) {
        val binding = TransferItemBinding.bind(item)
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

}