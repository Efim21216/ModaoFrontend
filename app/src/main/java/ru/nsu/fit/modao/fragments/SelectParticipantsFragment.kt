package ru.nsu.fit.modao.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.nsu.fit.modao.adapter.AdapterListener
import ru.nsu.fit.modao.adapter.SelectAdapter
import ru.nsu.fit.modao.databinding.FragmentSelectParticipantsBinding
import ru.nsu.fit.modao.models.ParticipantEvent

class SelectParticipantsFragment: Fragment(), SelectAdapter.CustomListener {
    private var _binding: FragmentSelectParticipantsBinding? = null
    private val binding get() = _binding!!
    private val participants: ArrayList<ParticipantEvent> = ArrayList()
    private val participantsEvent: ArrayList<ParticipantEvent> = ArrayList()
    private var isInit = false

    private val adapter = SelectAdapter()
    private val args by navArgs<SelectParticipantsFragmentArgs>()
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

        if (!isInit){
            initRecycler()
        }
        binding.participantRecycler.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.participantRecycler.adapter = adapter

        binding.buttonNext.setOnClickListener(){
            val send: Array<ParticipantEvent> = participantsEvent.toTypedArray()
            findNavController().navigate(SelectParticipantsFragmentDirections
                .actionSelectParticipantsFragmentToEnterCoefficientsFragment(send, args.cost, args.sponsor))
        }
    }

    override fun onClickItem(button: CheckBox, user: ParticipantEvent) {
        if (button.isChecked){
            participantsEvent.add(user)
            user.selected = true
        } else {
            participantsEvent.remove(user)
            user.selected = false
        }
    }
    private fun initRecycler(){
        isInit = true
        participants.add(ParticipantEvent(username = "Efim", id = 1, coefficient = 1f))
        participants.add(ParticipantEvent(username = "Nikita", id = 2, coefficient = 1f))
        participants.add(ParticipantEvent(username = "Peter", id = 3, coefficient = 1f))
        participants.add(ParticipantEvent(username = "Olga", id = 4, coefficient = 1f))
        adapter.attachListener(this)
        adapter.setList(participants)
    }

}