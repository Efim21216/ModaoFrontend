package ru.nsu.fit.modao.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.provider.MediaStore.Audio.Radio
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.nsu.fit.modao.adapter.AdapterListener
import ru.nsu.fit.modao.adapter.ParticipantsAdapter
import ru.nsu.fit.modao.databinding.FragmentEnterCostBinding
import ru.nsu.fit.modao.models.ParticipantEvent
import ru.nsu.fit.modao.repository.Repository
import ru.nsu.fit.modao.utils.App
import ru.nsu.fit.modao.viewmodels.CreateExpenseViewModel
import ru.nsu.fit.modao.viewmodels.RepositoryViewModelFactory


class EnterCostFragment : Fragment(), ParticipantsAdapter.CustomListener,
    ParticipantsAdapter.InitListener {
    private var _binding: FragmentEnterCostBinding? = null
    private val binding get() = _binding!!
    private var lastSelected: RadioButton? = null
    private val adapter: ParticipantsAdapter = ParticipantsAdapter()
    private lateinit var createExpenseViewModel: CreateExpenseViewModel
    private var app: App? = null
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

        app = activity?.application as App
        val repository = Repository(app!!)
        val viewModelFactory = RepositoryViewModelFactory(repository)
        createExpenseViewModel = ViewModelProvider(
            requireParentFragment().requireParentFragment(),
            viewModelFactory
        )[CreateExpenseViewModel::class.java]

        adapter.attachListener(this)
        adapter.attachInitListener(this)
        val list = createExpenseViewModel.participants.value ?: arrayOf()
        adapter.setList(list)
        binding.participantRecycler.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.participantRecycler.adapter = adapter

        createExpenseViewModel.participants.observe(viewLifecycleOwner){
            it.forEach { user -> user.coefficient = 1f }
            adapter.setList(it)
        }
    }

    override fun onClickItem(button: RadioButton, user: ParticipantEvent) {
        if (user.isSponsor){
            user.isSponsor = false
            lastSelected?.isChecked = false
            createExpenseViewModel.sponsor = null
        } else {
            lastSelected?.isChecked = false
            lastSelected = button
            createExpenseViewModel.sponsor?.isSponsor = false
            button.isChecked = true
            user.isSponsor = true
            createExpenseViewModel.sponsor = user
        }
    }

    override fun initItem(button: RadioButton, user: ParticipantEvent) {
        lastSelected = button
        createExpenseViewModel.sponsor = user
    }

}
