package ru.nsu.fit.modao.fragments

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import ru.nsu.fit.modao.adapter.CoefficientAdapter
import ru.nsu.fit.modao.databinding.FragmentEnterCoefficientsBinding
import ru.nsu.fit.modao.models.ParticipantEvent
import ru.nsu.fit.modao.viewmodels.CreateExpenseViewModel
@AndroidEntryPoint
class EnterCoefficientsFragment : Fragment(), CoefficientAdapter.CustomListener {
    private var _binding: FragmentEnterCoefficientsBinding? = null
    private val binding get() = _binding!!
    private val createExpenseViewModel: CreateExpenseViewModel by viewModels({requireParentFragment().requireParentFragment()})
    private val adapter = CoefficientAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEnterCoefficientsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter.attachListener(this)
        val list = createExpenseViewModel.participants.value ?: arrayOf()
        adapter.setList(list)
        binding.participantRecycler.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.participantRecycler.adapter = adapter
    }
    override fun onEditText(value: Editable?, participant: ParticipantEvent) {
        participant.assumedCoefficient = value?.toString()
    }
}