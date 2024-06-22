package com.example.trainmate.homepage

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.trainmate.R
import com.example.trainmate.controller.UserProfileController
import com.example.trainmate.entity.UserData
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

class CalorieCalculatorActivity : AppCompatActivity(){
    private val controller = UserProfileController()
    private var uidEmail: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        uidEmail = intent.getStringExtra("uID_email")
        setContentView(R.layout.activity_calorie_calculator)
        //spinner
        val activities = resources.getStringArray(R.array.Activities)
        val spinner: Spinner = findViewById(R.id.calorie_needs_calculator_spinner)
        //fields
        val ageInput: EditText = findViewById(R.id.age_input)
        val heightInput: EditText = findViewById(R.id.height_input)
        val weightInput: EditText = findViewById(R.id.weight_input)
        val genderGroup: RadioGroup = findViewById(R.id.gender_group)
        val calculateBtn: Button = findViewById(R.id.calculate_btn)

        val activityFactors = arrayOf(1.2, 1.375, 1.55, 1.725, 1.9)

        lifecycleScope.launch {
            val userProfile = controller.getUserData(uidEmail!!)

            ageInput.setText(userProfile?.age.toString())
            heightInput.setText(userProfile?.height.toString())
            weightInput.setText(userProfile?.weight.toString())
        }



        if (spinner != null) {
            val adapter = ArrayAdapter(this,
                android.R.layout.simple_spinner_item, activities)
            spinner.adapter = adapter

            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>,
                                            view: View, position: Int, id: Long) {
                    Toast.makeText(this@CalorieCalculatorActivity,
                        getString(R.string.selected_item) + " " +
                                "" + activities[position], Toast.LENGTH_SHORT).show()
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }
        }
        calculateBtn.setOnClickListener {
            val age = ageInput.text.toString().toIntOrNull() ?: 0
            val height = heightInput.text.toString().toIntOrNull() ?: 0
            val weight = weightInput.text.toString().toIntOrNull() ?: 0
            val selectedGenderId = genderGroup.checkedRadioButtonId
            val selectedGender = if (selectedGenderId != -1) findViewById<RadioButton>(selectedGenderId).text.toString() else "Not selected"
            val selectedActivityIndex = spinner.selectedItemPosition
            val selectedActivity = activities[selectedActivityIndex]
            val activityFactor = activityFactors[selectedActivityIndex]

            val bmr = calculateBMR(weight, height, age, selectedGender)
            val dailyCalories = (bmr * activityFactor).roundToInt()
            val dailyCaloriesLoss = (dailyCalories * 0.9).roundToInt()
            val dailyCaloriesGain = (dailyCalories * 1.1).roundToInt()

            lifecycleScope.launch {
                controller.updateUserData(uidEmail!!, UserData(calories = dailyCalories))
            }

            val message = """
                Daily Caloric Needs: ${dailyCalories} kcal
                Daily Caloric Needs for weight loss: ${dailyCaloriesLoss} kcal
                Daily Caloric Needs for weight gain: ${dailyCaloriesGain} kcal
            """.trimIndent()

            val builder = AlertDialog.Builder(this)
            builder.setTitle("Calorie Needs")
            builder.setMessage(message)
            builder.setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }

            val dialog: AlertDialog = builder.create()
            dialog.show()
        }
    }

    private fun calculateBMR(weight: Int, height: Int, age: Int, gender: String, ): Double {

        return if (gender == "Male") {
            (10 * weight) + (6.25 * height) - (5 * age) + 5
        } else if (gender == "Female") {
            (10 * weight) + (6.25 * height) - (5 * age) - 161
        } else {
            0.0
        }
    }
}


