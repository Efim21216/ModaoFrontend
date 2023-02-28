package ru.nsu.fit.modao.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.nsu.fit.modao.databinding.FragmentFriendsBinding
import ru.nsu.fit.modao.models.User

class FriendsFragment: Fragment() {
    private var _binding: FragmentFriendsBinding? = null
    private val binding get() = _binding!!
    //private val adapter: ExpensesAdapter = ExpensesAdapter()
    private val friends: Array<User> = arrayOf(
            User(username = "Olga", phone_number = "89009001010", bank = "Bank1"),
            User(username = "Efim", phone_number = "89009001020", bank = "Bank2"),
            User(username = "Petr", phone_number = "89009001030", bank = "Bank1"),
            User(username = "Nikita", phone_number = "89009001040", bank = "Bank2")
    )
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
    }
}