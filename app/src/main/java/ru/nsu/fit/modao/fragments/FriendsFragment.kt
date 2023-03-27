package ru.nsu.fit.modao.fragments

import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.nsu.fit.modao.R
import ru.nsu.fit.modao.adapter.AdapterListener
import ru.nsu.fit.modao.adapter.FriendsAdapter
import ru.nsu.fit.modao.databinding.FilterExpensesBinding
import ru.nsu.fit.modao.databinding.FragmentFriendsBinding
import ru.nsu.fit.modao.models.User
import ru.nsu.fit.modao.repository.Repository
import ru.nsu.fit.modao.utils.App
import ru.nsu.fit.modao.viewmodels.MainViewModel
import ru.nsu.fit.modao.viewmodels.RepositoryViewModelFactory

class FriendsFragment : Fragment(), AdapterListener<User> {
    private var _binding: FragmentFriendsBinding? = null
    private val binding get() = _binding!!
    private lateinit var app: App
    private lateinit var mainViewModel: MainViewModel
    private val adapter = FriendsAdapter()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFriendsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        app = requireActivity().application as App
        adapter.setListener(this)
        binding.friendsRecycler.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        binding.friendsRecycler.adapter = adapter
        val repository = Repository(app)
        mainViewModel = ViewModelProvider(
            this,
            RepositoryViewModelFactory(repository)
        )[MainViewModel::class.java]

        binding.buttonAddFriend.setOnClickListener {
            val uuid = binding.AddByUUID.text.toString()
            mainViewModel.addFriend(uuid)
        }
        mainViewModel.listFriends.observe(viewLifecycleOwner) {
            adapter.setFriendsList(it)
        }
        mainViewModel.getListFriends()
        mainViewModel.tipMessage.observe(viewLifecycleOwner) {
            val builder = AlertDialog.Builder(context)
            builder.setTitle(it)
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