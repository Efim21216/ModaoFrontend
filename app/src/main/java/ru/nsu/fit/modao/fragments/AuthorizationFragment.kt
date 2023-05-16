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
import ru.nsu.fit.modao.databinding.FragmentAuthorizationBinding
import ru.nsu.fit.modao.models.Authorization
import ru.nsu.fit.modao.utils.App
import ru.nsu.fit.modao.utils.Constants.Companion.ACCESS_TOKEN
import ru.nsu.fit.modao.utils.Constants.Companion.ID_USER
import ru.nsu.fit.modao.viewmodels.LoginViewModel
import javax.inject.Inject


@AndroidEntryPoint
class AuthorizationFragment : Fragment() {
    private var _binding: FragmentAuthorizationBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var app: App
    private val loginViewModel: LoginViewModel by viewModels()
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
        loginViewModel.message.observe(viewLifecycleOwner) {
            binding.tipMessage.text = it
        }
        loginViewModel.token.observe(viewLifecycleOwner) {
            login(it)
        }
        binding.buttonLogIn.setOnClickListener {
            binding.tipMessage.setText(R.string.wait)
            loginViewModel.login(
                login = binding.personLogin.text.toString(),
                password = binding.personPassword.text.toString()
            )
        }
        binding.buttonSignUp.setOnClickListener {
            findNavController().navigate(AuthorizationFragmentDirections
                .actionAuthorizationFragmentToRegistrationFragment())
        }
    }
    private fun login(it: Authorization){
        app.userId = it.id
        app.accessToken = it.accessToken
        app.refreshToken = it.refreshToken
        val edit = app.encryptedSharedPreferences.edit()
        edit.putString(ACCESS_TOKEN, it.accessToken).putLong(ID_USER, it.id).apply()
        activity?.findViewById<BottomNavigationView>(R.id.bottomMenu)?.visibility = View.VISIBLE
        findNavController().navigate(AuthorizationFragmentDirections.actionAuthorizationFragmentToProfileFragment())
    }
}