package ru.nsu.fit.modao.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.nsu.fit.modao.R
import ru.nsu.fit.modao.databinding.FragmentProfileBinding
import ru.nsu.fit.modao.repository.Repository
import ru.nsu.fit.modao.utils.App
import ru.nsu.fit.modao.viewmodels.MainViewModel
import ru.nsu.fit.modao.viewmodels.RepositoryViewModelFactory

class ProfileFragment: Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var app: App
    private lateinit var mainViewModel: MainViewModel
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
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
        mainViewModel.user.observe(viewLifecycleOwner){
            binding.personName.text = it.username
            binding.personBank.text = it.bank
            binding.personPhone.text = it.phone_number
            binding.personUuid.text = it.uuid
        }
        mainViewModel.getUser()
        binding.logOutLayout.setOnClickListener {
            activity?.findViewById<BottomNavigationView>(R.id.bottomMenu)?.visibility = View.GONE
            val sharedPreferences = app.encryptedSharedPreferences
            sharedPreferences.edit().clear().apply()
            findNavController().navigate(ProfileFragmentDirections
                .actionProfileFragmentToAuthorizationFragment())
        }
    }

}