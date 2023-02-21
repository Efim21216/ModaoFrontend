package ru.nsu.fit.modao.fragments

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.provider.MediaStore.Audio.Radio
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.nsu.fit.modao.adapter.AdapterListener
import ru.nsu.fit.modao.adapter.ParticipantsAdapter
import ru.nsu.fit.modao.databinding.FragmentEnterCostBinding
import ru.nsu.fit.modao.models.ParticipantEvent
import ru.nsu.fit.modao.models.User


class EnterCostFragment: Fragment(), ParticipantsAdapter.CustomListener {
    private var _binding: FragmentEnterCostBinding? = null
    private val binding get() = _binding!!
    private var lastSelected: CompoundButton? = null
    private var lastUser: ParticipantEvent? = null
    private val adapter: ParticipantsAdapter = ParticipantsAdapter()
    private val userList: ArrayList<ParticipantEvent> = ArrayList()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentEnterCostBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userList.add(ParticipantEvent(username = "Efim", id = 1))
        userList.add(ParticipantEvent(username = "Nikita", id = 2))
        userList.add(ParticipantEvent(username = "Peter", id = 3))
        userList.add(ParticipantEvent(username = "Olga", id = 4))

        adapter.attachListener(this)
        adapter.setList(userList)
        binding.participantRecycler.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.participantRecycler.adapter = adapter
        binding.buttonNext.setOnClickListener(){
            var builder = AlertDialog.Builder(context).setPositiveButton("OK"){ _, _ -> }
            if (lastSelected == null){
                builder.setMessage("Select participant").create().show()
                return@setOnClickListener
            }
            var cost: Float
            try {
                 cost = binding.editCost.text.toString().toFloat()
            } catch (e: NumberFormatException){
                builder.setMessage("Enter the cost").create().show()
                return@setOnClickListener
            }
            findNavController().navigate(EnterCostFragmentDirections
                .actionEnterCostFragmentToSelectParticipantsFragment(cost, lastUser!!))
        }
    }

    override fun onClickItem(button: RadioButton, user: ParticipantEvent) {
        if (button == lastSelected){
            button.isChecked = false
            lastSelected = null
            lastUser = null
        }
        else {
            lastSelected?.isChecked = false
            lastSelected = button
            lastUser = user
        }
    }

}
