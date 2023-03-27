package ru.nsu.fit.modao.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.nsu.fit.modao.adapter.AdapterListener
import ru.nsu.fit.modao.adapter.FriendsAdapter
import ru.nsu.fit.modao.databinding.FragmentGroupMembersBinding
import ru.nsu.fit.modao.models.User
import ru.nsu.fit.modao.repository.Repository
import ru.nsu.fit.modao.utils.App
import ru.nsu.fit.modao.viewmodels.MainViewModel
import ru.nsu.fit.modao.viewmodels.RepositoryViewModelFactory

class GroupMembersFragment : Fragment(), AdapterListener<User> {
    private var _binding: FragmentGroupMembersBinding? = null
    private val binding get() = _binding!!
    private lateinit var mainViewModel: MainViewModel
    private lateinit var app: App
    private val args by navArgs<GroupMembersFragmentArgs>()
    private val adapter = FriendsAdapter()


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
        mainViewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]

        adapter.setListener(this)
        binding.recyclerMembers.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        val list : Array<User> = mainViewModel.usersInGroup.value ?: arrayOf()
        adapter.setFriendsList(list)
        binding.recyclerMembers.adapter = adapter
        mainViewModel.usersInGroup.observe(viewLifecycleOwner){
            adapter.setFriendsList(it)
        }

        mainViewModel.getUsersInGroup(args.group.id!!)
        /*
        binding.buttonAddMember.setOnClickListener {
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
            mainViewModel.addUserToGroup(args.group.id!!, id)
        }
        */
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