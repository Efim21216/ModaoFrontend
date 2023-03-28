package ru.nsu.fit.modao

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupWindow
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.nsu.fit.modao.adapter.SelectAdapter
import ru.nsu.fit.modao.databinding.FragmentAddMemberBinding
import ru.nsu.fit.modao.models.ParticipantEvent

class NewMemberPopup(private val context: Context, private val root: ViewGroup): PopupWindow(context) {
    private val adapter = SelectAdapter()
    init {
        setupView()
    }
    private fun setupView(){
        val view =
            LayoutInflater.from(context).inflate(R.layout.fragment_add_member, root, false)
        val binding = FragmentAddMemberBinding.bind(view)
        isFocusable = true
        binding.newMemberRecycler.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        adapter.setList(arrayOf(ParticipantEvent("Efim", 1, 1, false)))
        binding.newMemberRecycler.adapter = adapter
        contentView = view
    }
    fun attachListener(listener: SelectAdapter.CustomListener){
        adapter.attachListener(listener)
    }
    fun setList(list: Array<ParticipantEvent>){
        adapter.setList(list)
    }
}