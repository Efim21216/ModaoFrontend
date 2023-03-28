package ru.nsu.fit.modao.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.nsu.fit.modao.R
import ru.nsu.fit.modao.databinding.MenuItemBinding

class MenuAdapter: RecyclerView.Adapter<MenuAdapter.MenuHolder>() {
    private var listener: AdapterListener<String>? = null
    private lateinit var list: List<String>

    fun attachListener(listener: AdapterListener<String>){
        this.listener = listener
    }
    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: List<String>){
        this.list = list
        notifyDataSetChanged()
    }
    class MenuHolder(item: View): RecyclerView.ViewHolder(item){
        val view = item
        val binding = MenuItemBinding.bind(item)
        fun bind(elem: String, listener: AdapterListener<String>){
            binding.menuItem.text = elem
            view.setOnClickListener {
                listener.onClickItem(elem)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.menu_item, parent, false)
        return MenuHolder(view)
    }

    override fun onBindViewHolder(holder: MenuHolder, position: Int) {
        holder.bind(list[position], listener!!)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}