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
    /*
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val args = Bundle()
        args.putBoolean("notification", true)
        args.putLong("groupId", 14)
        navController.navigate(R.id.nested_groups, args)*/

       /* val action = intent.getStringExtra("action")
        findNavController(R.id.fragment).navigate(R.id.notification_fragment)
        if (action != null){

        } else {
            Log.d("MyTag", "Empty action")
        }*/
    }
}