package ru.nsu.fit.modao

import android.os.Bundle
import android.util.Log
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
            if (!findNavController(R.id.fragment).popBackStack(item.itemId, inclusive = false) ) {
                findNavController(R.id.fragment).navigate(item.itemId)
            }
            return@setOnItemSelectedListener true
        }
    }
}