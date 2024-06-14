package com.example.trainmate.homepage

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import com.example.trainmate.BaseActivity
import com.example.trainmate.R

class HomePageActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)

        val calorieTrackButton = findViewById<Button>(R.id.calorie_tracking_btn)
        calorieTrackButton.setOnClickListener{
            val intent = Intent(this, CalorieTrackActivity::class.java)
            startActivity(intent)
        }

        val weightTrackButton = findViewById<Button>(R.id.weight_tracking_btn)
        weightTrackButton.setOnClickListener{
            val intent = Intent(this, WeightTrackActivity::class.java)
            startActivity(intent)
        }

        val alertsButton = findViewById<Button>(R.id.alerts_btn)
        alertsButton.setOnClickListener{
            val intent = Intent(this, AlertsActivity::class.java)
            startActivity(intent)
        }

        val calorieCalculatorButton = findViewById<Button>(R.id.calorie_calculator_btn)
        calorieCalculatorButton.setOnClickListener{
            val intent = Intent(this, CalorieCalculatorActivity::class.java)
            startActivity(intent)
        }

        val profileButton = findViewById<Button>(R.id.profile_btn)
        profileButton.setOnClickListener{
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
    }
}
