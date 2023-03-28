package ru.nsu.fit.modao.adapter

import android.util.Log
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
    private var list: Array<ParticipantEvent> = arrayOf()

    fun attachListener(listener: CustomListener){
        this.listener = listener
    }
    fun setList(list: Array<ParticipantEvent>){
        this.list = list
        Log.d("MyTag", "Set list")
        notifyDataSetChanged()
    }
    fun unselectAllParticipants(){
        list.forEach { participant -> participant.selected = false }
        notifyItemRangeChanged(0, list.size)
    }
    fun selectAllParticipants(){
        list.forEach { participant -> participant.selected = true }
        notifyItemRangeChanged(0, list.size)
    }
    class SelectHolder(item: View): RecyclerView.ViewHolder(item){
        val binding = ParticipantItemBinding.bind(item)
        fun bind(elem: ParticipantEvent, listener: CustomListener){
            Log.d("MyTag", "Select holder")
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
        Log.d("MyTag", "Select bind")
        holder.bind(list[position], listener!!)
    }

    override fun getItemCount(): Int {
        return list.size
    }
    interface CustomListener{
        fun onClickItem(button: CheckBox, participantEvent: ParticipantEvent)
    }
}