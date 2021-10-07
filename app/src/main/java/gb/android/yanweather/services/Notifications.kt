package gb.android.yanweather.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import gb.android.yanweather.R
import gb.android.yanweather.view.MainActivity


class Notifications : FirebaseMessagingService() {

    companion object {
        private const val PUSH_KEY_TITLE = "title"
        private const val PUSH_KEY_MESSAGE = "message"

        private const val NOTIFICATION_ID = 1
        private const val CHANNEL_HIGH_PRIORITY = "CHANNEL_HIGH_PRIORITY"
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        val remoteMessageData = remoteMessage.data

        if (remoteMessageData.isNotEmpty()) {
            val title = remoteMessageData[PUSH_KEY_TITLE]
            val message = remoteMessageData[PUSH_KEY_MESSAGE]
            if (!title.isNullOrBlank() && !message.isNullOrBlank()) {
                pushNotification(title, message)
            }
        }
    }

    private fun pushNotification(title: String, message: String) {
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        val intentAction = Intent(this, MainActivity::class.java)

        intentAction.putExtra("action", "actionName")

        val intent = PendingIntent.getActivity(
            this, 0,
            intentAction, 0
        )

        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_HIGH_PRIORITY).apply {
            setSmallIcon(R.drawable.ic_notification)
            setContentTitle(title)
            setContentText(message)
            addAction(R.drawable.ic_launcher_background, "Open app", intent)
            priority = NotificationCompat.PRIORITY_HIGH
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelName = "Name ${CHANNEL_HIGH_PRIORITY}"
            val channelDesc = "Description ${CHANNEL_HIGH_PRIORITY}"
            val channelImportance = NotificationManager.IMPORTANCE_HIGH

            val channel =
                NotificationChannel(
                    CHANNEL_HIGH_PRIORITY,
                    channelName,
                    channelImportance
                )
                    .apply {
                        description = channelDesc
                    }

            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())
    }

}