package ru.nsu.fit.modao.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import ru.nsu.fit.modao.R
import ru.nsu.fit.modao.databinding.CoefficientItemBinding
import ru.nsu.fit.modao.models.ParticipantEvent

class CoefficientAdapter: RecyclerView.Adapter<CoefficientAdapter.CoefficientHolder>() {
    private var listener: CustomListener? = null
    private lateinit var list: List<ParticipantEvent>

    fun attachListener(listener: CustomListener){
        this.listener = listener
    }
    fun setList(list: List<ParticipantEvent>){
        this.list = list
        notifyDataSetChanged()
    }
    class CoefficientHolder(item: View): RecyclerView.ViewHolder(item){
        val binding = CoefficientItemBinding.bind(item)
        fun bind(elem: ParticipantEvent, listener: CustomListener){
            binding.nameParticipant.text = elem.username
            binding.editCoefficient.setText(elem.coefficient.toString())
            binding.editCoefficient.addTextChangedListener(){
                listener.onEditText(it.toString(), elem)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoefficientHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.coefficient_item, parent, false)
        return CoefficientHolder(view)
    }

    override fun onBindViewHolder(holder: CoefficientHolder, position: Int) {
        holder.bind(list[position], listener!!)
    }

    override fun getItemCount(): Int {
        return list.size
    }
    interface CustomListener {
        fun onEditText(value: String, participant: ParticipantEvent)
    }
}