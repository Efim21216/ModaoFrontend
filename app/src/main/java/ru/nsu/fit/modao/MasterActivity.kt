package ru.nsu.fit.modao

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.nsu.fit.modao.databinding.ActivityMasterBinding
@AndroidEntryPoint
class MasterActivity: AppCompatActivity() {
    private lateinit var binding: ActivityMasterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMasterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomMenu.setOnItemSelectedListener {item ->
            when (item.itemId) {
                R.id.nested_groups -> findNavController(R.id.fragment)
                    .navigate(R.id.action_global_nested_groups)
                R.id.notification_fragment -> findNavController(R.id.fragment)
                    .navigate(R.id.action_global_notification_fragment)
                R.id.friends_fragment -> findNavController(R.id.fragment)
                    .navigate(R.id.action_global_friends_fragment)
                R.id.profile_fragment -> findNavController(R.id.fragment)
                    .navigate(R.id.action_global_profile_fragment)
                else -> findNavController(R.id.fragment)
                    .navigate(item.itemId)
            }
            return@setOnItemSelectedListener true
        }
    }

}