package ru.nsu.fit.modao.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.nsu.fit.modao.adapter.AdapterListener
import ru.nsu.fit.modao.adapter.FriendsAdapter
import ru.nsu.fit.modao.databinding.FragmentFriendsBinding
import ru.nsu.fit.modao.models.User

class FriendsFragment: Fragment(), AdapterListener<User> {
    private var _binding: FragmentFriendsBinding? = null
    private val binding get() = _binding!!
    private val friends: Array<User> = arrayOf(
            User(username = "Olga", phone_number = "89009001010", bank = "Bank1"),
            User(username = "Efim", phone_number = "89009001020", bank = "Bank2"),
            User(username = "Petr", phone_number = "89009001030", bank = "Bank1"),
            User(username = "Nikita", phone_number = "89009001040", bank = "Bank2")
    )
    private val adapter = FriendsAdapter()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentFriendsBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter.setListener(this)
        adapter.setFriendsList(friends)
        binding.friendsRecycler.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        binding.friendsRecycler.adapter = adapter
    }

    override fun onClickItem(item: User) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(item.username)
        builder.setMessage("Phone: " + item.phone_number + "\n" + "Bank: " + item.bank)
        builder.setPositiveButton("OK") { _, _ -> }
        builder.create().show()
    }
}