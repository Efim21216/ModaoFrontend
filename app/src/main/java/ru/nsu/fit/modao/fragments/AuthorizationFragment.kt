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
import ru.nsu.fit.modao.databinding.FragmentAuthorizationBinding
import ru.nsu.fit.modao.repository.Repository
import ru.nsu.fit.modao.utils.App
import ru.nsu.fit.modao.viewmodels.LoginViewModel
import ru.nsu.fit.modao.viewmodels.LoginViewModelFactory

class AuthorizationFragment : Fragment() {
    private var _binding: FragmentAuthorizationBinding? = null
    private val binding get() = _binding!!
    private var app: App? = null
    private lateinit var loginViewModel: LoginViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAuthorizationBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        app = activity?.application as App
        val repository = Repository(app!!)
        val viewModelFactory = LoginViewModelFactory(repository)
        loginViewModel = ViewModelProvider(this, viewModelFactory)[LoginViewModel::class.java]
        loginViewModel.message.observe(viewLifecycleOwner) {
            binding.tipMessage.text = it
        }
        loginViewModel.userId.observe(viewLifecycleOwner) {
            app!!.userId = it
            activity?.findViewById<BottomNavigationView>(R.id.bottomMenu)?.visibility = View.VISIBLE
            findNavController().navigate(AuthorizationFragmentDirections.actionAuthorizationFragmentToProfileFragment())
        }
        binding.buttonLogIn.setOnClickListener(){
            binding.tipMessage.setText(R.string.wait)
            loginViewModel.login(
                login = binding.personLogin.text.toString(),
                password = binding.personPassword.text.toString()
            )
        }
        binding.buttonSignUp.setOnClickListener(){
            findNavController().navigate(AuthorizationFragmentDirections
                .actionAuthorizationFragmentToRegistrationFragment())
        }
    }
}