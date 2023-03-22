package ru.nsu.fit.modao.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.nsu.fit.modao.R
import ru.nsu.fit.modao.databinding.GroupItemBinding
import ru.nsu.fit.modao.models.Group

class GroupAdapter: RecyclerView.Adapter<GroupAdapter.GroupHolder>() {
    private var listener: AdapterListener<Group>? = null
    private var groupsList: Array<Group> = arrayOf()

    fun attachListener(listener: AdapterListener<Group>){
        this.listener = listener
    }
    fun setGroups(groupsList: Array<Group>){
        this.groupsList = groupsList
        notifyDataSetChanged()
    }
    class GroupHolder(item: View): RecyclerView.ViewHolder(item){
        val binding = GroupItemBinding.bind(item)
        val view = item
        fun bind(group: Group, listener: AdapterListener<Group>){
            binding.nameGroup.text = group.groupName
            view.setOnClickListener(){
                listener.onClickItem(group)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.group_item, parent, false)
        return GroupHolder(view)
    }

    override fun onBindViewHolder(holder: GroupHolder, position: Int) {
        holder.bind(groupsList[position], listener!!)
    }

    override fun getItemCount(): Int {
        return groupsList.size
    }
}