package ru.nsu.fit.modao.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import ru.nsu.fit.modao.adapter.SelectAdapter
import ru.nsu.fit.modao.databinding.FragmentSelectParticipantsBinding
import ru.nsu.fit.modao.models.ParticipantEvent
import ru.nsu.fit.modao.viewmodels.CreateExpenseViewModel
@AndroidEntryPoint
class SelectParticipantsFragment: Fragment(), SelectAdapter.CustomListener {
    private var _binding: FragmentSelectParticipantsBinding? = null
    private val binding get() = _binding!!
    private val createExpenseViewModel: CreateExpenseViewModel by viewModels({requireParentFragment().requireParentFragment()})
    private val adapter = SelectAdapter()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSelectParticipantsBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter.attachListener(this)
        binding.participantRecycler.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.participantRecycler.adapter = adapter

        createExpenseViewModel.participants.observe(viewLifecycleOwner){
            adapter.setList(it)
        }
        binding.buttonSelect.setOnClickListener {
            adapter.selectAllParticipants()
        }
        binding.buttonUnselect.setOnClickListener {
            adapter.unselectAllParticipants()
        }
    }

    override fun onClickItem(button: CheckBox, participantEvent: ParticipantEvent) {
        participantEvent.selected = button.isChecked
    }
}