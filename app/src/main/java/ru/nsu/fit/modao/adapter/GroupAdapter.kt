package ru.nsu.fit.modao.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.nsu.fit.modao.R
import ru.nsu.fit.modao.databinding.GroupItemArchiveBinding
import ru.nsu.fit.modao.databinding.GroupItemBinding
import ru.nsu.fit.modao.models.Group

class GroupAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var listener: AdapterListener<Group>? = null
    private var groupsList: Array<Group> = arrayOf()

    companion object {
        const val ACTIVE = 0
        const val ARCHIVE = 1
    }
    fun attachListener(listener: AdapterListener<Group>){
        this.listener = listener
    }
    fun setGroups(groupsList: Array<Group>){
        this.groupsList = groupsList
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            ACTIVE -> GroupHolder(inflater.inflate(R.layout.group_item, parent, false))
            ARCHIVE -> ArchiveGroupHolder(inflater.inflate(R.layout.group_item_archive, parent, false))
            else -> throw IllegalArgumentException("Unknown type $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is GroupHolder -> holder.bind(groupsList[position], listener!!)
            is ArchiveGroupHolder -> holder.bind(groupsList[position], listener!!)
        }

    }

    override fun getItemCount(): Int {
        return groupsList.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (groupsList[position].typeGroup) {
            0 -> ACTIVE
            1 -> ARCHIVE
            else -> throw IllegalArgumentException("Unknown type ${groupsList[position].typeGroup}")
        }
    }

    class GroupHolder(item: View): RecyclerView.ViewHolder(item){
        val binding = GroupItemBinding.bind(item)
        val view = item
        fun bind(group: Group, listener: AdapterListener<Group>){
            binding.nameGroup.text = group.groupName
            binding.nameGroup.setOnClickListener {
                listener.onClickItem(group)
            }
        }
    }
    class ArchiveGroupHolder(item: View): RecyclerView.ViewHolder(item) {
        val binding = GroupItemArchiveBinding.bind(item)
        val view = item
        fun bind(group: Group, listener: AdapterListener<Group>){
            binding.nameGroup.text = group.groupName
            binding.nameGroup.setOnClickListener {
                listener.onClickItem(group)
            }
        }
    }
}