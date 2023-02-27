package ru.nsu.fit.modao.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.nsu.fit.modao.adapter.CoefficientAdapter
import ru.nsu.fit.modao.databinding.FragmentEnterCoefficientsBinding
import ru.nsu.fit.modao.models.ParticipantEvent
import ru.nsu.fit.modao.repository.Repository
import ru.nsu.fit.modao.utils.App
import ru.nsu.fit.modao.viewmodels.CreateExpenseViewModel
import ru.nsu.fit.modao.viewmodels.RepositoryViewModelFactory

class EnterCoefficientsFragment : Fragment(), CoefficientAdapter.CustomListener {
    private var _binding: FragmentEnterCoefficientsBinding? = null
    private val binding get() = _binding!!
    private lateinit var createExpenseViewModel: CreateExpenseViewModel
    private var app: App? = null
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

        app = activity?.application as App
        val repository = Repository(app!!)
        val viewModelFactory = RepositoryViewModelFactory(repository)
        createExpenseViewModel =
            ViewModelProvider(
                requireParentFragment().requireParentFragment(),
                viewModelFactory
            )[CreateExpenseViewModel::class.java]
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