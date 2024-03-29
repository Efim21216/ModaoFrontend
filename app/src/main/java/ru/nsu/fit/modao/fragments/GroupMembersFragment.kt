package ru.nsu.fit.modao.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import ru.nsu.fit.modao.adapter.AdapterListener
import ru.nsu.fit.modao.adapter.FriendsAdapter
import ru.nsu.fit.modao.databinding.FragmentGroupMembersBinding
import ru.nsu.fit.modao.models.User
import ru.nsu.fit.modao.viewmodels.MainViewModel
@AndroidEntryPoint
class GroupMembersFragment : Fragment(), AdapterListener<User> {
    private var _binding: FragmentGroupMembersBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel: MainViewModel by viewModels()
    private val args by navArgs<GroupMembersFragmentArgs>()
    private val adapter = FriendsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGroupMembersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter.setListener(this)
        binding.recyclerMembers.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        val list: Array<User> = mainViewModel.usersInGroup.value ?: arrayOf()
        adapter.setFriendsList(list)
        binding.recyclerMembers.adapter = adapter
        mainViewModel.usersInGroup.observe(viewLifecycleOwner) {
            adapter.setFriendsList(it)
        }

        mainViewModel.getUsersInGroup(args.group.id!!)
        binding.buttonAddMember.setOnClickListener {
            findNavController().navigate(GroupMembersFragmentDirections
                .actionGroupMembersFragmentToAddMemberFragment(args.group))
        }
        mainViewModel.tipMessage.observe(viewLifecycleOwner) {
            val builder = AlertDialog.Builder(context)
            builder.setMessage(it)
            builder.setPositiveButton("OK") { _, _ -> }
            builder.create().show()
        }

    }

    override fun onClickItem(item: User) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(item.username)
        builder.setMessage("Phone: " + item.phone_number + "\n" + "Bank: " + item.bank)
        builder.setPositiveButton("OK") { _, _ -> }
        builder.create().show()
    }


}