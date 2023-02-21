package ru.nsu.fit.modao.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.RadioButton
import androidx.recyclerview.widget.RecyclerView
import ru.nsu.fit.modao.R
import ru.nsu.fit.modao.databinding.ParticipantItemBinding
import ru.nsu.fit.modao.databinding.ParticipantSpentItemBinding
import ru.nsu.fit.modao.models.ParticipantEvent

class ParticipantsAdapter: RecyclerView.Adapter<ParticipantsAdapter.ParticipantsHolder>() {
    private var listener: CustomListener? = null
    private var list = ArrayList<ParticipantEvent>()

    fun attachListener(listener: CustomListener){
        this.listener = listener
    }
    fun setList(list: ArrayList<ParticipantEvent>){
        this.list = list
        notifyDataSetChanged()
    }
    class ParticipantsHolder(item: View): RecyclerView.ViewHolder(item){
        val binding = ParticipantSpentItemBinding.bind(item)
        fun bind(user: ParticipantEvent, listener: CustomListener){
            binding.nameParticipant.text = user.username
            binding.selectParticipant.setOnClickListener(){
                listener.onClickItem(it as RadioButton, user)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParticipantsHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.participant_spent_item, parent, false)
        return ParticipantsHolder(view)
    }

    override fun onBindViewHolder(holder: ParticipantsHolder, position: Int) {
        holder.bind(list[position], listener!!)
    }

    override fun getItemCount(): Int {
        return list.size
    }
    interface CustomListener{
        fun onClickItem(button: RadioButton, user: ParticipantEvent)
    }
}