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
import ru.nsu.fit.modao.databinding.FragmentRegistrationBinding
import ru.nsu.fit.modao.repository.Repository
import ru.nsu.fit.modao.utils.App
import ru.nsu.fit.modao.viewmodels.LoginViewModel
import ru.nsu.fit.modao.viewmodels.RepositoryViewModelFactory

class RegistrationFragment: Fragment() {
    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = _binding!!
    private var app: App? = null
    private var login: String? = null
    private var password: String? = null
    private lateinit var loginViewModel: LoginViewModel
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)
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
        val viewModelFactory = RepositoryViewModelFactory(repository)
        loginViewModel = ViewModelProvider(this, viewModelFactory)[LoginViewModel::class.java]
        loginViewModel.message.observe(viewLifecycleOwner) {
            binding.tipMessage.text = it
        }
        loginViewModel.userId.observe(viewLifecycleOwner) {
            loginViewModel.login(login = login!!, password = password!!)
        }
        loginViewModel.token.observe(viewLifecycleOwner) {
            app!!.userId = it.id
            app!!.accessToken = it.accessToken
            app!!.refreshToken = it.refreshToken
            activity?.findViewById<BottomNavigationView>(R.id.bottomMenu)?.visibility = View.VISIBLE
            findNavController().navigate(RegistrationFragmentDirections.actionRegistrationFragmentToProfileFragment())
        }
        binding.buttonContinue.setOnClickListener {
            binding.tipMessage.setText(R.string.wait)
            password = binding.createPassword.text.toString()
            login = binding.createLogin.text.toString()
            loginViewModel.createUser(login!!, password!!,
            binding.createUsername.text.toString())
        }
    }
}