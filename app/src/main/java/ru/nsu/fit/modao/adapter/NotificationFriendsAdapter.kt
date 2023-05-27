package ru.nsu.fit.modao.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.nsu.fit.modao.R
import ru.nsu.fit.modao.databinding.NotificationItemBinding
import ru.nsu.fit.modao.models.Notification

class NotificationFriendsAdapter: RecyclerView.Adapter<NotificationFriendsAdapter.NotificationHolder>() {
    private var listenerFriend: AdapterListener<Notification>? = null
    private var listenerGroup: AdapterListener<Notification>? = null
    private var list: MutableList<Notification> = mutableListOf()

    fun attachListenerFriend(listener: AdapterListener<Notification>){
        this.listenerFriend = listener
    }
    fun attachListenerGroup(listener: AdapterListener<Notification>){
        this.listenerGroup = listener
    }
    fun setList(list: Array<Notification>){
        this.list = list.toMutableList()
        notifyDataSetChanged()
    }
    fun addToList(list: Array<Notification>){
        val start = this.list.size - 1
        this.list.addAll(list)
        notifyItemRangeChanged(start, list.size)
    }
    fun removeItem(item: Notification){
        val id = list.indexOf(item)
        list.remove(item)
        notifyItemRemoved(id)
    }
    class NotificationHolder(item: View): RecyclerView.ViewHolder(item){
        val binding = NotificationItemBinding.bind(item)
        fun bind(elem: Notification, listenerFriend: AdapterListener<Notification>,
                 listenerGroup: AdapterListener<Notification>){
            if (elem.nameGroup != null){
                binding.nameNewGroup.visibility = View.VISIBLE
                binding.nameNewGroup.text = elem.nameGroup
                binding.newGroup.visibility = View.VISIBLE
                binding.root.setOnClickListener {
                    Log.d("MyTag", "Notification adapter group")
                    listenerGroup.onClickItem(elem)
                }
            } else {
                binding.nameNewFriend.visibility = View.VISIBLE
                binding.newFriend.visibility = View.VISIBLE
                binding.nameNewFriend.text = elem.username
                binding.root.setOnClickListener {
                    listenerFriend.onClickItem(elem)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.notification_item, parent, false)
        return NotificationHolder(view)
    }

    override fun onBindViewHolder(holder: NotificationHolder, position: Int) {
        holder.bind(list[position], listenerFriend!!,listenerGroup!!)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}