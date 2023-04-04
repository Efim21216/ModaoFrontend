package ru.nsu.fit.modao.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ru.nsu.fit.modao.R
import ru.nsu.fit.modao.adapter.ParticipantsAdapter
import ru.nsu.fit.modao.databinding.FragmentSelectSecondParticipantBinding
import ru.nsu.fit.modao.models.ParticipantEvent
import ru.nsu.fit.modao.repository.Repository
import ru.nsu.fit.modao.utils.App
import ru.nsu.fit.modao.viewmodels.MainViewModel
import ru.nsu.fit.modao.viewmodels.RepositoryViewModelFactory


class SelectSecondParticipantFragment : BottomSheetDialogFragment(),
    ParticipantsAdapter.CustomListener,
    ParticipantsAdapter.InitListener {

    private var _binding: FragmentSelectSecondParticipantBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<AddMemberFragmentArgs>()
    private lateinit var mainViewModel: MainViewModel
    private lateinit var app: App
    private val adapter = ParticipantsAdapter()
    private var lastSelected: RadioButton? = null
    private var lastUser: ParticipantEvent? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSelectSecondParticipantBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        app = requireActivity().application as App
        val repository = Repository(app)
        mainViewModel = ViewModelProvider(
            this,
            RepositoryViewModelFactory(repository)
        )[MainViewModel::class.java]

        mainViewModel.getUsersInGroup(args.group.id!!)
        mainViewModel.usersInGroup.observe(viewLifecycleOwner) {
            val list = it.filter { user -> user.id !=  app.userId}.map { user ->
                ParticipantEvent(
                    username = user.username,
                    id = user.id,
                    selected = false,
                    isSponsor = false
                )
            }.toTypedArray()
            if (list.isEmpty()) {
                binding.buttonDone.visibility = View.GONE
                binding.tipDialog.text = getString(R.string.addMoreMember)
            }
            adapter.setList(list)
        }
        adapter.attachListener(this)
        adapter.attachInitListener(this)
        binding.newMemberRecycler.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.newMemberRecycler.adapter = adapter
        binding.buttonDone.setOnClickListener {
            val action = SelectSecondParticipantFragmentDirections
                .actionSelectSecondParticipantFragmentToCreateAnExpenseFragment(args.group)
            action.second = lastUser
            findNavController().navigate(action)
        }
    }

    override fun onClickItem(button: RadioButton, user: ParticipantEvent) {
        if (user.isSponsor){
            user.isSponsor = false
            lastSelected?.isChecked = false
            lastUser = null
        } else {
            lastSelected?.isChecked = false
            lastSelected = button
            lastUser?.isSponsor = false
            button.isChecked = true
            user.isSponsor = true
            lastUser = user
        }
    }

    override fun initItem(button: RadioButton, user: ParticipantEvent) {
        button.isChecked = false
        user.selected = false
    }

}