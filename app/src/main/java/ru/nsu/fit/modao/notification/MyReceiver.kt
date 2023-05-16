package ru.nsu.fit.modao.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class MyReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        /*Log.d("MyTag", "Received")
        val extras = intent?.extras
        
        extras?.keySet()?.firstOrNull { it == KEY_ACTION }?. let { key ->
            when (extras.getString(key)) {
                ACTION_MOVE_TO_NOTIFICATION -> {
                    extras.getString(KEY_MESSAGE)?.let{
                        Log.d("MyTag", it)
                    }
                }
                else -> Log.d("MyTag", "Fail")
            }
        }*/
    }
}