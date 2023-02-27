package ru.nsu.fit.modao.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.nsu.fit.modao.adapter.AdapterListener
import ru.nsu.fit.modao.adapter.SelectAdapter
import ru.nsu.fit.modao.databinding.FragmentSelectParticipantsBinding
import ru.nsu.fit.modao.models.ParticipantEvent
import ru.nsu.fit.modao.repository.Repository
import ru.nsu.fit.modao.utils.App
import ru.nsu.fit.modao.viewmodels.CreateExpenseViewModel
import ru.nsu.fit.modao.viewmodels.RepositoryViewModelFactory

class SelectParticipantsFragment: Fragment(), SelectAdapter.CustomListener {
    private var _binding: FragmentSelectParticipantsBinding? = null
    private val binding get() = _binding!!
    private lateinit var createExpenseViewModel: CreateExpenseViewModel
    private var app: App? = null
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

        app = activity?.application as App
        val repository = Repository(app!!)
        val viewModelFactory = RepositoryViewModelFactory(repository)
        createExpenseViewModel = ViewModelProvider(
            requireParentFragment().requireParentFragment(),
            viewModelFactory
        )[CreateExpenseViewModel::class.java]

        adapter.attachListener(this)
        binding.participantRecycler.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.participantRecycler.adapter = adapter

        createExpenseViewModel.participants.observe(viewLifecycleOwner){
            adapter.setList(it)
        }
        binding.buttonSelect.setOnClickListener(){
            adapter.selectAllParticipants()
        }
        binding.buttonUnselect.setOnClickListener(){
            adapter.unselectAllParticipants()
        }
    }

    override fun onClickItem(button: CheckBox, user: ParticipantEvent) {
        user.selected = button.isChecked
    }
}