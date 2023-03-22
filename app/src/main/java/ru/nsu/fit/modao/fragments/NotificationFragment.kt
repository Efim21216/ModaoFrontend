package ru.nsu.fit.modao.fragments

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
import ru.nsu.fit.modao.models.UserDebt
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

        val acceptFriend = object: AdapterListener<Notification> {
            override fun onClickItem(item: Notification) {
                mainViewModel.acceptInvitationFriend(item.id!!)
            }
        }
        val denyFriend = object: AdapterListener<Notification> {
            override fun onClickItem(item: Notification) {
                mainViewModel.denyInvitationFriend(item.id!!)
            }
        }
        val acceptGroup = object: AdapterListener<Notification> {
            override fun onClickItem(item: Notification) {
                mainViewModel.acceptInvitationGroup(item.id!!)
            }
        }
        val denyGroup = object: AdapterListener<Notification> {
            override fun onClickItem(item: Notification) {
                mainViewModel.denyInvitationGroup(item.id!!)
            }
        }
        adapter.attachListenerAcceptFriend(acceptFriend)
        adapter.attachListenerDenyFriend(denyFriend)
        adapter.attachListenerAcceptGroup(acceptGroup)
        adapter.attachListenerDenyGroup(denyGroup)
        binding.recyclerNotification.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        binding.recyclerNotification.adapter = adapter

        app = requireActivity().application as App
        val repository = Repository(app)
        mainViewModel = ViewModelProvider(
            requireActivity(),
            RepositoryViewModelFactory(repository)
        )[MainViewModel::class.java]

        mainViewModel.getInvitationsGroup()

        mainViewModel.invitationUser.observe(viewLifecycleOwner) {
            adapter.setList(it)
        }

    }
}