package com.example.trainmate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView

class HomePageActivity : BaseActivity(), View.OnClickListener {

    private var calorieTrackButton: EditText? = null
    private var weightTrackButton: EditText? = null
    private var alertsButton: EditText? = null
    private var calorieCalculatorButton: EditText? = null
    private var profileButton: EditText? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)

//        calorieTrackButton = findViewById(R.id.calorie_tracking_btn)
//        weightTrackButton = findViewById(R.id.weight_tracking_btn)
//        alertsButton = findViewById(R.id.alerts_btn)
//        calorieCalculatorButton = findViewById(R.id.calorie_calculator_btn)
//        profileButton = findViewById(R.id.profile_btn)
//
//        calorieTrackButton?.setOnClickListener(this)
//        weightTrackButton?.setOnClickListener(this)
//        alertsButton?.setOnClickListener(this)
//        calorieCalculatorButton?.setOnClickListener(this)
//        profileButton?.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.calorie_tracking_btn -> {
                startActivity(Intent(this, NutritionActivity::class.java))
            }
//            R.id.weight_tracking_btn -> {
//                startActivity(Intent(this, WeightTrackingActivity::class.java))
//            }
//            R.id.alerts_btn -> {
//                startActivity(Intent(this, AlertsActivity::class.java))
//            }
//            R.id.calorie_calculator_btn -> {
//                startActivity(Intent(this, CalorieCalculatorActivity::class.java))
//            }
//            R.id.profile_btn -> {
//                startActivity(Intent(this, ProfileActivity::class.java))
//            }
        }
    }
}
