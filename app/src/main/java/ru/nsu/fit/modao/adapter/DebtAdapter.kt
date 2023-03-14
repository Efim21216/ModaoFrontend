package ru.nsu.fit.modao.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.nsu.fit.modao.R
import ru.nsu.fit.modao.databinding.ExpenseItemBinding
import ru.nsu.fit.modao.models.UserDebt

class DebtAdapter: RecyclerView.Adapter<DebtAdapter.DebtHolder>() {
    private var listener: AdapterListener<UserDebt>? = null
    private var list = ArrayList<UserDebt>()

    fun attachListener(listener: AdapterListener<UserDebt>){
        this.listener = listener
    }
    fun setList(list: ArrayList<UserDebt>){
        this.list = list
        notifyDataSetChanged()
    }
    class DebtHolder(item: View): RecyclerView.ViewHolder(item){
        val binding = ExpenseItemBinding.bind(item)
        fun bind(expense: UserDebt) = with(binding){
            shortInfo.text = expense.username
            amountExpense.text = expense.debt.toString()
            // TODO check currency
            if (expense.debt!! <= 0){
                currencyImage.setImageResource(R.drawable.ic_profit_rub)
            } else {
                currencyImage.setImageResource(R.drawable.ic_loss_rub)
                amountExpense.setTextColor(Color.parseColor("#D46E6E"))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DebtHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.expense_item, parent, false)
        return DebtHolder(view)
    }

    override fun onBindViewHolder(holder: DebtHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }
}