package com.example.trainmate.homepage

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import com.example.trainmate.R
import com.example.trainmate.notificationTutorial.NotificationTutorialActivity

class AlertsActivity : AppCompatActivity() {
    private lateinit var trainingAlertSwitch: Switch
    private lateinit var creatineAlertSwitch: Switch
    private lateinit var waterAlertSwitch: Switch
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alerts)

        sharedPreferences = getSharedPreferences("alert_prefs", Context.MODE_PRIVATE)

        trainingAlertSwitch = findViewById(R.id.training_alert_switch)
        creatineAlertSwitch = findViewById(R.id.creatine_alert_switch)
        waterAlertSwitch = findViewById(R.id.water_alert_switch)

        // Set switch states based on saved preferences
        trainingAlertSwitch.isChecked = sharedPreferences.getBoolean("training_alert_enabled", true)
        creatineAlertSwitch.isChecked = sharedPreferences.getBoolean("creatine_alert_enabled", true)
        waterAlertSwitch.isChecked = sharedPreferences.getBoolean("water_alert_enabled", true)

        // Set listeners on the switches
        trainingAlertSwitch.setOnCheckedChangeListener { _, isChecked ->
            Log.d("AlertsActivity", "Training Alert Switch: $isChecked")
            saveAlertPreference("training_alert_enabled", isChecked)
        }

        creatineAlertSwitch.setOnCheckedChangeListener { _, isChecked ->
            Log.d("AlertsActivity", "Creatine Alert Switch: $isChecked")
            saveAlertPreference("creatine_alert_enabled", isChecked)
        }

        waterAlertSwitch.setOnCheckedChangeListener { _, isChecked ->
            Log.d("AlertsActivity", "Water Alert Switch: $isChecked")
            saveAlertPreference("water_alert_enabled", isChecked)
        }

        val submitButton = findViewById<Button>(R.id.setupBtn)
        submitButton.setOnClickListener{
            val intent = Intent(this, NotificationTutorialActivity::class.java)
            startActivity(intent)
        }
    }

    private fun saveAlertPreference(key: String, isEnabled: Boolean) {
        with(sharedPreferences.edit()) {
            putBoolean(key, isEnabled)
            apply()
        }
    }
}
