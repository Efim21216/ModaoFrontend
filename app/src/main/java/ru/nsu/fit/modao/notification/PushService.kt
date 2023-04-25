package ru.nsu.fit.modao.notification

import com.google.firebase.messaging.FirebaseMessagingService

class PushService: FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    /*override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Log.d("MyTag", "message received")
        val intent = Intent(INTENT_FILTER)
        message.data.forEach { entity ->
            intent.putExtra(entity.key, entity.value)
        }
        sendBroadcast(intent)
    }*/

    companion object {
        const val INTENT_FILTER = "PUSH_EVENT"
        const val KEY_ACTION = "action"
        const val KEY_GROUP_ID = "groupID"

        const val TO_NOTIFICATION = "move to notification"
        const val TO_DATA_CONFIRMATION = "move to data confirmation"
    }
}