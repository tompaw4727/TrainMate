package com.example.trainmate.homepage

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import com.example.trainmate.BaseActivity
import com.example.trainmate.R
import com.example.trainmate.homepage.calorie.track.CalorieTrackActivity
import com.example.trainmate.notification.NotificationActivity

class HomePageActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_page_activity)

        val uid_email = intent.getStringExtra("uID_email")

        val calorieTrackButton = findViewById<Button>(R.id.calorie_tracking_btn)
        calorieTrackButton.setOnClickListener {
            val intent = Intent(this, CalorieTrackActivity::class.java)
            intent.putExtra("uID_email", uid_email)
            startActivity(intent)
        }

        val weightTrackButton = findViewById<Button>(R.id.weight_tracking_btn)
        weightTrackButton.setOnClickListener {
            val intent = Intent(this, CalorieTrackActivity::class.java)
            intent.putExtra("uID_email", uid_email)
            startActivity(intent)
        }

        val alertsButton = findViewById<Button>(R.id.alerts_btn)
        alertsButton.setOnClickListener {
            val intent = Intent(this, NotificationActivity::class.java)
            intent.putExtra("uID_email", uid_email)
            startActivity(intent)
        }

        val calorieCalculatorButton = findViewById<Button>(R.id.calorie_calculator_btn)
        calorieCalculatorButton.setOnClickListener {
            val intent = Intent(this, CalorieCalculatorActivity::class.java)
            intent.putExtra("uID_email", uid_email)
            startActivity(intent)
        }

        val profileButton = findViewById<Button>(R.id.profile_btn)
        profileButton.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            intent.putExtra("uID_email", uid_email)
            startActivity(intent)
        }
    }
}
