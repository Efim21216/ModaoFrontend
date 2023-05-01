package ru.nsu.fit.modao.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import ru.nsu.fit.modao.R
import ru.nsu.fit.modao.databinding.FragmentRegistrationBinding
import ru.nsu.fit.modao.utils.App
import ru.nsu.fit.modao.viewmodels.LoginViewModel
import javax.inject.Inject

@AndroidEntryPoint
class RegistrationFragment: Fragment() {
    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = _binding!!
    @Inject
    lateinit var app: App
    private var login: String? = null
    private var password: String? = null
    private val loginViewModel: LoginViewModel by viewModels()
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

        loginViewModel.message.observe(viewLifecycleOwner) {
            binding.tipMessage.text = it
        }
        loginViewModel.userId.observe(viewLifecycleOwner) {
            loginViewModel.login(login = login!!, password = password!!)
        }
        loginViewModel.token.observe(viewLifecycleOwner) {
            app.userId = it.id
            app.accessToken = it.accessToken
            app.refreshToken = it.refreshToken
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