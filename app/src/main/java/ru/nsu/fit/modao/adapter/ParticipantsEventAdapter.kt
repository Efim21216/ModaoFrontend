package ru.nsu.fit.modao.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.nsu.fit.modao.R
import ru.nsu.fit.modao.databinding.WhoParticipatedItemBinding
import ru.nsu.fit.modao.models.ParticipantEvent

class ParticipantsEventAdapter: RecyclerView.Adapter<ParticipantsEventAdapter.ParticipantEventHolder>() {
    private var list: Array<ParticipantEvent> = arrayOf()

    fun setList(list: Array<ParticipantEvent>){
        this.list = list
        notifyDataSetChanged()
    }
    class ParticipantEventHolder(item: View): RecyclerView.ViewHolder(item){
        val binding = WhoParticipatedItemBinding.bind(item)
        fun bind(elem: ParticipantEvent){
            binding.nameParticipant.text = elem.username
            binding.amount.text = elem.transferAmount
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParticipantEventHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.who_participated_item, parent, false)
        return ParticipantEventHolder(view)
    }

    override fun onBindViewHolder(holder: ParticipantEventHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }
}