package ru.nsu.fit.modao.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.nsu.fit.modao.R
import ru.nsu.fit.modao.databinding.FragmentStartBinding
import ru.nsu.fit.modao.repository.Repository
import ru.nsu.fit.modao.utils.App
import ru.nsu.fit.modao.utils.Constants.Companion.ACCESS_TOKEN
import ru.nsu.fit.modao.utils.Constants.Companion.ID_USER
import ru.nsu.fit.modao.viewmodels.MainViewModel
import ru.nsu.fit.modao.viewmodels.RepositoryViewModelFactory

class StartFragment : Fragment() {
    private var _binding: FragmentStartBinding? = null
    private val binding get() = _binding!!
    private lateinit var mainViewModel: MainViewModel
    private lateinit var  app: App
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentStartBinding.inflate(inflater, container, false)
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
        mainViewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        val sharedPreferences = app.encryptedSharedPreferences
        val accessToken: String? = sharedPreferences.getString(ACCESS_TOKEN, null)
        if (accessToken == null){
            findNavController().navigate(StartFragmentDirections.actionStartFragmentToAuthorizationFragment())
        } else {
            app.accessToken = accessToken
            app.userId = sharedPreferences.getLong(ID_USER, -1)
            binding.progressBar.visibility = View.VISIBLE
            mainViewModel.getUser()
        }
        mainViewModel.tipMessageStart.observe(viewLifecycleOwner){
            app.accessToken = null
            findNavController().navigate(StartFragmentDirections.actionStartFragmentToAuthorizationFragment())
        }
        mainViewModel.user.observe(viewLifecycleOwner){
            activity?.findViewById<BottomNavigationView>(R.id.bottomMenu)?.visibility = View.VISIBLE
            findNavController().navigate(StartFragmentDirections.actionStartFragmentToProfileFragment())
        }
        mainViewModel.messageHandler.observe(viewLifecycleOwner){
            findNavController().navigate(StartFragmentDirections.actionStartFragmentToAuthorizationFragment())
        }
    }


}