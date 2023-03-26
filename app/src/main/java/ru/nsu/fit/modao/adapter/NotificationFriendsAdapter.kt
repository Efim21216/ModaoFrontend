package ru.nsu.fit.modao.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.nsu.fit.modao.R
import ru.nsu.fit.modao.databinding.NotificationItemBinding
import ru.nsu.fit.modao.models.Notification

class NotificationFriendsAdapter: RecyclerView.Adapter<NotificationFriendsAdapter.NotificationHolder>() {
    private var acceptFriend: AdapterListener<Notification>? = null
    private var denyFriend: AdapterListener<Notification>? = null
    private var acceptGroup: AdapterListener<Notification>? = null
    private var denyGroup: AdapterListener<Notification>? = null
    private var list: MutableList<Notification> = mutableListOf()

    fun attachListenerAcceptFriend(listener: AdapterListener<Notification>){
        this.acceptFriend = listener
    }
    fun attachListenerDenyFriend(listener: AdapterListener<Notification>){
        this.denyFriend = listener
    }
    fun attachListenerAcceptGroup(listener: AdapterListener<Notification>){
        this.acceptGroup = listener
    }
    fun attachListenerDenyGroup(listener: AdapterListener<Notification>){
        this.denyGroup = listener
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
        fun bind(elem: Notification, acceptFriend: AdapterListener<Notification>,
                 denyFriend: AdapterListener<Notification>, acceptGroup: AdapterListener<Notification>,
                 denyGroup: AdapterListener<Notification>){
            if (elem.nameGroup != null){
                binding.infoNotification.text = elem.username + " invites to " + elem.nameGroup
                binding.buttonAccept.setOnClickListener {
                    acceptGroup.onClickItem(elem)
                }
                binding.buttonDeny.setOnClickListener {
                    denyGroup.onClickItem(elem)
                }
            } else {
                binding.infoNotification.text = elem.username
                binding.buttonAccept.setOnClickListener {
                    acceptFriend.onClickItem(elem)
                }
                binding.buttonDeny.setOnClickListener {
                    denyFriend.onClickItem(elem)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.notification_item, parent, false)
        return NotificationHolder(view)
    }

    override fun onBindViewHolder(holder: NotificationHolder, position: Int) {
        holder.bind(list[position], acceptFriend!!, denyFriend!!, acceptGroup!!, denyGroup!!)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}