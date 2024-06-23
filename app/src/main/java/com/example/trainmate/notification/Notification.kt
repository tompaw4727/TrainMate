package com.example.trainmate.notification

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.trainmate.R

const val channelID = "channel1"
const val titleExtra = "titleExtra"
const val messageExtra = "messageExtra"
const val notificationIdExtra = "notificationId"

class Notification : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Log.d("Notification", "onReceive called")
        val notificationId = intent.getIntExtra(notificationIdExtra, 0) // Retrieve the unique notification ID
        val notification = NotificationCompat.Builder(context, channelID)
            .setSmallIcon(R.drawable.ic_bell)
            .setContentTitle(intent.getStringExtra(titleExtra))
            .setContentText(intent.getStringExtra(messageExtra))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(notificationId, notification) // Use the unique notification ID
        Log.d("Notification", "Notification sent with ID $notificationId")
    }
}