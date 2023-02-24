package ru.nsu.fit.modao.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import ru.nsu.fit.modao.R
import ru.nsu.fit.modao.databinding.ParticipantItemBinding
import ru.nsu.fit.modao.models.ParticipantEvent

class SelectAdapter: RecyclerView.Adapter<SelectAdapter.SelectHolder>() {
    private var listener: CustomListener? = null
    private var list = ArrayList<ParticipantEvent>()

    fun attachListener(listener: CustomListener){
        this.listener = listener
    }
    fun setList(list: ArrayList<ParticipantEvent>){
        this.list = list
        notifyDataSetChanged()
    }
    class SelectHolder(item: View): RecyclerView.ViewHolder(item){
        val binding = ParticipantItemBinding.bind(item)
        fun bind(elem: ParticipantEvent, listener: CustomListener){
            binding.nameParticipant.text = elem.username
            binding.checkParticipant.isChecked = elem.selected
            binding.checkParticipant.setOnClickListener(){
                listener.onClickItem(it as CheckBox, elem)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.participant_item, parent, false)
        return SelectHolder(view)
    }

    override fun onBindViewHolder(holder: SelectHolder, position: Int) {
        holder.bind(list[position], listener!!)
    }

    override fun getItemCount(): Int {
        return list.size
    }
    interface CustomListener{
        fun onClickItem(button: CheckBox, participantEvent: ParticipantEvent)
    }
}