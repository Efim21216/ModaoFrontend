package ru.nsu.fit.modao.fragments

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import ru.nsu.fit.modao.R
import ru.nsu.fit.modao.databinding.FragmentProfileBinding
import ru.nsu.fit.modao.utils.App
import ru.nsu.fit.modao.utils.Constants.Companion.FAIL
import ru.nsu.fit.modao.utils.Constants.Companion.SUCCESS
import ru.nsu.fit.modao.viewmodels.MainViewModel
import javax.inject.Inject
@AndroidEntryPoint
class ProfileFragment: Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel: MainViewModel by viewModels()
    @Inject
    lateinit var app: App
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

        mainViewModel.user.observe(viewLifecycleOwner){
            binding.personName.text = it.username
            binding.personUuid.text = it.uuid
        }
        mainViewModel.getUser()
        binding.logOutLayout.setOnClickListener {
            mainViewModel.exit()
        }
        binding.personUuid.setOnLongClickListener {
            val clipboard = context?.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager?
            val clip = ClipData.newPlainText("text", binding.personUuid.text.toString())
            clipboard?.setPrimaryClip(clip)
            Toast.makeText(context, "Copied!", Toast.LENGTH_LONG).show()
            true
        }
        mainViewModel.tipMessage.observe(viewLifecycleOwner) {
            when (it) {
                FAIL -> Toast.makeText(context,
                "Check internet connection", Toast.LENGTH_LONG).show()
                SUCCESS -> {
                    activity?.findViewById<BottomNavigationView>(R.id.bottomMenu)?.visibility = View.GONE
                    val sharedPreferences = app.encryptedSharedPreferences
                    sharedPreferences.edit().clear().apply()
                    findNavController().navigate(ProfileFragmentDirections
                        .actionProfileFragmentToAuthorizationFragment())
                }
            }
        }
    }

}