package ru.nsu.fit.modao.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import ru.nsu.fit.modao.adapter.ParticipantsAdapter
import ru.nsu.fit.modao.databinding.FragmentExpenseWhoSpentBinding
import ru.nsu.fit.modao.models.ParticipantEvent
import ru.nsu.fit.modao.viewmodels.CreateExpenseViewModel

@AndroidEntryPoint
class ExpenseWhoSpentFragment : Fragment(), ParticipantsAdapter.CustomListener,
    ParticipantsAdapter.InitListener {
    private var _binding: FragmentExpenseWhoSpentBinding? = null
    private val binding get() = _binding!!
    private var lastSelected: RadioButton? = null
    private val adapter: ParticipantsAdapter = ParticipantsAdapter()
    private val createExpenseViewModel: CreateExpenseViewModel by viewModels({requireParentFragment().requireParentFragment()})
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExpenseWhoSpentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
