package ru.nsu.fit.modao.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import ru.nsu.fit.modao.R
import ru.nsu.fit.modao.databinding.FragmentStartBinding
import ru.nsu.fit.modao.models.Tokens
import ru.nsu.fit.modao.notification.PushService.Companion.KEY_ACTION
import ru.nsu.fit.modao.notification.PushService.Companion.KEY_GROUP_ID
import ru.nsu.fit.modao.notification.PushService.Companion.TO_DATA_CONFIRMATION
import ru.nsu.fit.modao.notification.PushService.Companion.TO_NOTIFICATION
import ru.nsu.fit.modao.utils.App
import ru.nsu.fit.modao.utils.Constants.Companion.ACCESS_TOKEN
import ru.nsu.fit.modao.utils.Constants.Companion.ID_USER
import ru.nsu.fit.modao.utils.Constants.Companion.REFRESH_TOKEN
import ru.nsu.fit.modao.viewmodels.MainViewModel
import javax.inject.Inject

@AndroidEntryPoint
class StartFragment : Fragment() {
    private var _binding: FragmentStartBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel: MainViewModel by viewModels()
    @Inject
    lateinit var  app: App
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

        val sharedPreferences = app.encryptedSharedPreferences
        val refreshToken = sharedPreferences.getString(REFRESH_TOKEN, null)
        if (refreshToken == null){
            findNavController().navigate(StartFragmentDirections.actionStartFragmentToAuthorizationFragment())
        } else {
            mainViewModel.getRefreshToken(Tokens(refreshToken = refreshToken))
        }
        mainViewModel.tokens.observe(viewLifecycleOwner) {
            app.accessToken = it.accessToken
            if (it.refreshToken != null) {
                app.refreshToken = it.refreshToken
                Log.d("MyTag", "New refresh")
            }
            val edit = sharedPreferences.edit()
            edit.putString(ACCESS_TOKEN, it.accessToken)
                .putString(REFRESH_TOKEN, it.refreshToken).apply()
            app.userId = sharedPreferences.getLong(ID_USER, -1)
            binding.progressBar.visibility = View.VISIBLE
            mainViewModel.getUser()
        }
        mainViewModel.tipMessage.observe(viewLifecycleOwner){
            app.accessToken = null
            findNavController().navigate(StartFragmentDirections.actionStartFragmentToAuthorizationFragment())
        }
        mainViewModel.user.observe(viewLifecycleOwner){
            activity?.findViewById<BottomNavigationView>(R.id.bottomMenu)?.visibility = View.VISIBLE
            if (!checkNotification()){
                findNavController().navigate(StartFragmentDirections.actionStartFragmentToProfileFragment())
            }
        }
        mainViewModel.messageHandler.observe(viewLifecycleOwner){
            findNavController().navigate(StartFragmentDirections.actionStartFragmentToAuthorizationFragment())
        }

    }
    private fun checkNotification(): Boolean {
        val extras = activity?.intent?.extras
        extras?.keySet()?.firstOrNull {key -> key == KEY_ACTION}?.let {key ->
            when (extras.getString(key)) {
                TO_NOTIFICATION -> {
                    findNavController().navigate(R.id.notification_fragment)
                    return true
                }
                TO_DATA_CONFIRMATION -> {
                    extras.getString(KEY_GROUP_ID)?.toLong()?.let {id ->
                        val args = Bundle()
                        args.putBoolean("notification", true)
                        args.putLong("groupId", id)
                        findNavController().navigate(R.id.nested_groups, args)
                        return true
                    }
                }
                else -> {
                    Log.e("MyTag", "Unknown key")
                }
            }
        }
        return false
    }

}