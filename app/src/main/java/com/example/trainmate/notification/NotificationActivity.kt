package com.example.trainmate.notification

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import com.example.trainmate.R
import com.example.trainmate.databinding.ActivityNotificationBinding
import java.util.*

class NotificationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNotificationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        createNotificationChannel()
        binding.submitButton.setOnClickListener { scheduleNotifications() }
    }

    private fun scheduleNotifications() {
        val time = getTime()

        if (findViewById<Switch>(R.id.training_alert_switch).isChecked) {
            Log.d("NotificationTutorial", "Training Alert switch is checked")
            scheduleNotification("Training Alert", "Time for training!", time, 1)
        }

        if (findViewById<Switch>(R.id.creatine_alert_switch).isChecked) {
            Log.d("NotificationTutorial", "Creatine Alert switch is checked")
            scheduleNotification("Creatine Alert", "Time to take creatine!", time, 2)
        }

        if (findViewById<Switch>(R.id.water_alert_switch).isChecked) {
            Log.d("NotificationTutorial", "Water Alert switch is checked")
            scheduleNotification("Water Alert", "Time to drink water!", time, 3)
        }

        showAlert(time)
    }


    private fun scheduleNotification(title: String, message: String, time: Long, notificationId: Int) {
        val intent = Intent(applicationContext, Notification::class.java)
        intent.putExtra("titleExtra", title)
        intent.putExtra("messageExtra", message)
        intent.putExtra("notificationId", notificationId)

        val pendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            notificationId,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                if (alarmManager.canScheduleExactAlarms()) {
                    alarmManager.setRepeating(
                        AlarmManager.RTC_WAKEUP,
                        time,
                        AlarmManager.INTERVAL_DAY,
                        pendingIntent
                    )
                    Log.d("NotificationTutorial", "Exact alarm scheduled successfully for $title with ID $notificationId")
                } else {
                    Log.e("NotificationTutorial", "The app doesn't have permission to schedule exact alarms")
                }
            } else {
                alarmManager.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    time,
                    AlarmManager.INTERVAL_DAY,
                    pendingIntent
                )
                Log.d("NotificationTutorial", "Repeating alarm scheduled successfully for $title with ID $notificationId")
            }
        } catch (e: SecurityException) {
            Log.e("NotificationTutorial", "SecurityException: ${e.message}")
        }
    }

    private fun showAlert(time: Long) {
        val date = Date(time)
        val timeFormat = android.text.format.DateFormat.getTimeFormat(applicationContext)

        AlertDialog.Builder(this)
            .setTitle("Notifications Scheduled")
            .setMessage("Notifications scheduled at: ${timeFormat.format(date)} every day")
            .setPositiveButton("Okay") { _, _ -> }
            .show()
    }

    private fun getTime(): Long {
        val minute = binding.timePicker.minute
        val hour = binding.timePicker.hour

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)

        if (calendar.timeInMillis <= System.currentTimeMillis()) {
            calendar.add(Calendar.DAY_OF_MONTH, 1)
        }
        Log.d("NotificationTutorial", "Notification time set to: ${calendar.time}")
        return calendar.timeInMillis
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Notif Channel"
            val desc = "A Description of the Channel"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelID, name, importance)
            channel.description = desc
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
            Log.d("NotificationTutorial", "Notification channel created")
        }
    }
}