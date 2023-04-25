package ru.nsu.fit.modao

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import ru.nsu.fit.modao.databinding.ActivityMasterBinding

class MasterActivity: AppCompatActivity() {
    private lateinit var binding: ActivityMasterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMasterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomMenu.setOnItemSelectedListener {
            findNavController(R.id.fragment).navigate(it.itemId)
            return@setOnItemSelectedListener true
        }
    }
}