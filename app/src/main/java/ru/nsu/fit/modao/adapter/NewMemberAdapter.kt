package ru.nsu.fit.modao.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.nsu.fit.modao.R
import ru.nsu.fit.modao.databinding.ParticipantItemBinding
import ru.nsu.fit.modao.models.ParticipantEvent
import ru.nsu.fit.modao.models.User

class NewMemberAdapter: RecyclerView.Adapter<NewMemberAdapter.NewMemberHolder>() {
    private var listener: AdapterListener<ParticipantEvent>? = null
    private var list: Array<ParticipantEvent> = arrayOf()

    fun attachListener(listener: AdapterListener<ParticipantEvent>){
        this.listener = listener
    }
    fun setList(list: Array<ParticipantEvent>){
        this.list = list
        notifyDataSetChanged()
    }
    class NewMemberHolder(item: View): RecyclerView.ViewHolder(item){
        val binding = ParticipantItemBinding.bind(item)
        fun bind(elem: ParticipantEvent, listener: AdapterListener<ParticipantEvent>){
            binding.nameParticipant.text = elem.username
            binding.checkParticipant.setOnClickListener {
                listener.onClickItem(elem)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewMemberHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.participant_item, parent, false)
        return NewMemberHolder(view)
    }

    override fun onBindViewHolder(holder: NewMemberHolder, position: Int) {
        holder.bind(list[position], listener!!)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}