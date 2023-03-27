package ru.nsu.fit.modao.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.nsu.fit.modao.adapter.AdapterListener
import ru.nsu.fit.modao.adapter.NotificationFriendsAdapter
import ru.nsu.fit.modao.databinding.FragmentNotificationBinding
import ru.nsu.fit.modao.models.Notification
import ru.nsu.fit.modao.repository.Repository
import ru.nsu.fit.modao.utils.App
import ru.nsu.fit.modao.viewmodels.MainViewModel
import ru.nsu.fit.modao.viewmodels.RepositoryViewModelFactory


class NotificationFragment : Fragment() {
    private var _binding: FragmentNotificationBinding? = null
    private val binding get() = _binding!!
    private lateinit var app: App
    private val adapter = NotificationFriendsAdapter()
    private lateinit var mainViewModel: MainViewModel
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentNotificationBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val friendListener = object: AdapterListener<Notification> {
            override fun onClickItem(item: Notification) {
                listenerFriend(item)
            }
        }
        val groupListener = object: AdapterListener<Notification> {
            override fun onClickItem(item: Notification) {
                Log.d("MyTag", "Notification group")
                listenerGroup(item)
            }
        }
        adapter.attachListenerFriend(friendListener)
        adapter.attachListenerGroup(groupListener)
        binding.recyclerNotification.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        binding.recyclerNotification.adapter = adapter

        app = requireActivity().application as App
        val repository = Repository(app)
        mainViewModel = ViewModelProvider(
            this,
            RepositoryViewModelFactory(repository)
        )[MainViewModel::class.java]

        mainViewModel.getInvitationsFriend()
        mainViewModel.getInvitationsGroup()

        mainViewModel.invitationUser.observe(viewLifecycleOwner) {
            adapter.addToList(it)
        }
        mainViewModel.tipMessage.observe(viewLifecycleOwner) {
            if (it == "Fail"){
                val builder = AlertDialog.Builder(context)
                builder.setTitle("Server problems...")
                builder.setPositiveButton("OK") { _, _ -> }
                builder.create().show()
            }
        }
    }
    private fun listenerFriend(item: Notification){
        val builder = AlertDialog.Builder(context)
        builder.setTitle("New friend")
        builder.setMessage(item.username + " wants to add you to the friends")
        builder.setNegativeButton("Deny") {_, _ ->
            mainViewModel.denyInvitationFriend(item.id!!)
            adapter.removeItem(item)
        }
        builder.setPositiveButton("Accept") { _, _ ->
            mainViewModel.acceptInvitationFriend(item.id!!)
            adapter.removeItem(item)
        }
        builder.create().show()
    }
    private fun listenerGroup(item: Notification){
        Log.d("MyTag", "Notification listener")
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Invitation to a group")
        builder.setMessage(item.username + " invites you to the group " + item.nameGroup)
        builder.setNegativeButton("Deny") {_, _ ->
            mainViewModel.denyInvitationGroup(item.id!!)
            adapter.removeItem(item)
        }
        builder.setPositiveButton("Accept") { _, _ ->
            mainViewModel.acceptInvitationGroup(item.id!!)
            adapter.removeItem(item)
        }
        builder.create().show()
    }
}