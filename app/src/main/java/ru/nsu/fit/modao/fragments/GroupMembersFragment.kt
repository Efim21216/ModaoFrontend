package ru.nsu.fit.modao.fragments

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.nsu.fit.modao.adapter.SelectAdapter
import ru.nsu.fit.modao.databinding.FragmentGroupMembersBinding
import ru.nsu.fit.modao.models.ParticipantEvent
import ru.nsu.fit.modao.repository.Repository
import ru.nsu.fit.modao.utils.App
import ru.nsu.fit.modao.viewmodels.MainViewModel
import ru.nsu.fit.modao.viewmodels.RepositoryViewModelFactory

class GroupMembersFragment : Fragment(), SelectAdapter.CustomListener {
    private var _binding: FragmentGroupMembersBinding? = null
    private val binding get() = _binding!!
    private lateinit var mainViewModel: MainViewModel
    private lateinit var app: App
    private val args by navArgs<GroupMembersFragmentArgs>()
    private val adapter = SelectAdapter()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentGroupMembersBinding.inflate(inflater, container, false)
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
        val viewModelFactory = RepositoryViewModelFactory(repository)
        mainViewModel = ViewModelProvider(requireActivity(), viewModelFactory)[MainViewModel::class.java]

        adapter.attachListener(this)
        binding.recyclerMembers.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        val list = mainViewModel.usersInGroup.value?.map {user -> ParticipantEvent(username = user.username) } ?: listOf<ParticipantEvent>()
        adapter.setList(list.toTypedArray())
        binding.recyclerMembers.adapter = adapter
        mainViewModel.usersInGroup.observe(viewLifecycleOwner){
            adapter.setList(it.map {user -> ParticipantEvent(username = user.username) }.toTypedArray())
        }
        mainViewModel.getUsersInGroup(args.group.id!!)
        binding.buttonAdd.setOnClickListener {
            val id: Long
            try {
                id = binding.editIdUser.text?.toString()?.toLong()!!
            } catch (e: NumberFormatException){
                val builder = AlertDialog.Builder(context)
                builder.setMessage("Enter ID")
                builder.setPositiveButton("OK") { _, _ -> }
                builder.create().show()
                return@setOnClickListener
            }
            mainViewModel.addUserToGroup(app.userId, args.group.id!!, id)
        }


    }

    override fun onClickItem(button: CheckBox, participantEvent: ParticipantEvent) {
        TODO("Not yet implemented")
    }

}