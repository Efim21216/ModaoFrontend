package ru.nsu.fit.modao.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ru.nsu.fit.modao.R
import ru.nsu.fit.modao.adapter.AdapterListener
import ru.nsu.fit.modao.adapter.NewMemberAdapter
import ru.nsu.fit.modao.databinding.FragmentAddMemberBinding
import ru.nsu.fit.modao.models.ParticipantEvent
import ru.nsu.fit.modao.models.User
import ru.nsu.fit.modao.repository.Repository
import ru.nsu.fit.modao.utils.App
import ru.nsu.fit.modao.viewmodels.MainViewModel
import ru.nsu.fit.modao.viewmodels.RepositoryViewModelFactory

class AddMemberFragment : BottomSheetDialogFragment(), AdapterListener<ParticipantEvent> {
    private var _binding: FragmentAddMemberBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<AddMemberFragmentArgs>()
    private lateinit var mainViewModel: MainViewModel
    private lateinit var app: App
    private var count = 1
    private lateinit var members: List<User>
    private var listFriends: Array<ParticipantEvent> = arrayOf()
    private val adapter = NewMemberAdapter()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddMemberBinding.inflate(inflater, container, false)
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
            mainViewModel.getListFriends()
            members = mainViewModel.usersInGroup.value?.toList()!!
        }
        adapter.setList(arrayOf(ParticipantEvent(username = "Efim", id = 1, selected = false)))
        mainViewModel.listFriends.observe(viewLifecycleOwner) {
            listFriends = it.filter{ user -> !members.contains(user)}.map { user ->
                ParticipantEvent(
                    username = user.username,
                    id = user.id,
                    selected = false
                )
            }.toTypedArray()
            if (listFriends.isEmpty()) {
                binding.buttonDone.visibility = View.GONE
                binding.tipDialog.text = getString(R.string.addMoreFriend)
            }
            adapter.setList(listFriends)
        }

        adapter.attachListener(this)
        binding.newMemberRecycler.adapter = adapter
        binding.newMemberRecycler.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)

        binding.buttonDone.setOnClickListener {
            val list = listFriends.filter { friend -> friend.selected }
            count = list.size
            list.forEach { friend -> mainViewModel.addUserToGroup(args.group.id!!, friend.id!!) }
            findNavController().navigate(AddMemberFragmentDirections
                .actionAddMemberFragmentToGroupMembersFragment(args.group))
        }
    }

    override fun onClickItem(item: ParticipantEvent) {
        item.selected = !item.selected
    }
}

