package ru.nsu.fit.modao.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.nsu.fit.modao.adapter.AdapterListener
import ru.nsu.fit.modao.adapter.ParticipantsAdapter
import ru.nsu.fit.modao.databinding.FragmentEnterCostBinding
import ru.nsu.fit.modao.models.ParticipantEvent


class EnterCostFragment : Fragment(), ParticipantsAdapter.CustomListener {
    private var _binding: FragmentEnterCostBinding? = null
    private val binding get() = _binding!!
    private var lastUser: ParticipantEvent? = null
    private val adapter: ParticipantsAdapter = ParticipantsAdapter()
    private lateinit var userList: ArrayList<ParticipantEvent>
    private var isInit = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEnterCostBinding.inflate(inflater, container, false)
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
        binding.participantRecycler.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.participantRecycler.adapter = adapter
        binding.buttonNext.setOnClickListener() {
            var builder = AlertDialog.Builder(context).setPositiveButton("OK") { _, _ -> }
            if (lastUser == null) {
                builder.setMessage("Select participant").create().show()
                return@setOnClickListener
            }
            var cost: Float
            try {
                cost = binding.editCost.text.toString().toFloat()
            } catch (e: NumberFormatException) {
                builder.setMessage("Enter the cost").create().show()
                return@setOnClickListener
            }
            findNavController().navigate(
                EnterCostFragmentDirections
                    .actionEnterCostFragmentToSelectParticipantsFragment(cost, lastUser!!)
            )
        }
    }

    override fun onClickItem(button: RadioButton, user: ParticipantEvent) {
        if (user.selected) {
            button.isChecked = false
            user.selected = false
            lastUser = null
        } else {
            button.isChecked = true
            user.selected = true
            lastUser = user
        }
    }

    private fun initRecycler(){
        isInit = true
        userList = ArrayList()
        userList.add(ParticipantEvent(username = "Efim", id = 1))
        userList.add(ParticipantEvent(username = "Nikita", id = 2))
        userList.add(ParticipantEvent(username = "Peter", id = 3))
        userList.add(ParticipantEvent(username = "Olga", id = 4))
        adapter.attachListener(this)
        adapter.setList(userList)

    }
}
