package ru.nsu.fit.modao.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.nsu.fit.modao.adapter.CoefficientAdapter
import ru.nsu.fit.modao.databinding.FragmentEnterCoefficientsBinding
import ru.nsu.fit.modao.models.ParticipantEvent

class EnterCoefficientsFragment: Fragment(), CoefficientAdapter.CustomListener {

    private val args by navArgs<EnterCoefficientsFragmentArgs>()
    private val adapter = CoefficientAdapter()
    private lateinit var participants: Array<ParticipantEvent>
    private var _binding: FragmentEnterCoefficientsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentEnterCoefficientsBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        participants = args.participants
        adapter.attachListener(this)
        adapter.setList(participants.toList())
        binding.participantRecycler.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.participantRecycler.adapter = adapter

        binding.buttonNext.setOnClickListener(){
            participants.forEach {
            try {
                it.coefficient = it.assumedCoefficient?.toFloat()
            }catch (e: NumberFormatException){
                val builder = AlertDialog.Builder(context).setMessage("Incorrect coefficient")
                builder.setPositiveButton("OK"){_,_ ->}
                builder.create().show()
                return@setOnClickListener
            }
            }
            Toast.makeText(context, participants.size.toString(), Toast.LENGTH_LONG).show()
        }
    }

    override fun onEditText(value: String, participant: ParticipantEvent) {
        participants.get(participants.indexOf(participant)).assumedCoefficient = value
    }
}