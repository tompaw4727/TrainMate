package com.example.trainmate.homepage

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.lifecycle.lifecycleScope
import com.example.trainmate.BaseActivity
import com.example.trainmate.R
import com.example.trainmate.controller.UserProfileController
import com.example.trainmate.entity.UserData
import kotlinx.coroutines.launch

class ProfileActivity : BaseActivity() {

    private lateinit var nameEditText: EditText
    private lateinit var ageEditText: EditText
    private lateinit var sexSpinner: Spinner
    private lateinit var weightEditText: EditText
    private lateinit var heightEditText: EditText
    private lateinit var editButton: Button

    private var uidEmail: String? = null
    private val userProfileController = UserProfileController()
    private var isEditing = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_activity)

        nameEditText = findViewById(R.id.nameTextView)
        ageEditText = findViewById(R.id.ageTextView)
        sexSpinner = findViewById(R.id.sexSpinner)
        weightEditText = findViewById(R.id.weightTextView)
        heightEditText = findViewById(R.id.heightTextView)
        editButton = findViewById(R.id.edit_profile_btn)

        val sexOptions = arrayOf("Male", "Female")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, sexOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sexSpinner.adapter = adapter

        uidEmail = intent.getStringExtra("uID_email")

        lifecycleScope.launch {
            val userProfile = userProfileController.getUserData(uidEmail!!)

            nameEditText.setText(userProfile?.username)
            ageEditText.setText(userProfile?.age.toString())
            sexSpinner.setSelection(sexOptions.indexOf(userProfile?.sex))
            weightEditText.setText(userProfile?.weight.toString())
            heightEditText.setText(userProfile?.height.toString())

            toggleEditMode(false)
        }

        editButton.setOnClickListener {
            if (isEditing) {
                saveProfile()
            } else {
                toggleEditMode(true)
            }
        }
    }

    private fun toggleEditMode(editMode: Boolean) {
        isEditing = editMode

        nameEditText.isEnabled = editMode
        ageEditText.isEnabled = editMode
        sexSpinner.isEnabled = editMode
        weightEditText.isEnabled = editMode
        heightEditText.isEnabled = editMode

        editButton.text = if (editMode) "Save Profile" else "Edit Profile"
    }

    private fun saveProfile() {
        val username = nameEditText.text.toString()
        val age = ageEditText.text.toString().toIntOrNull()
        val sex = sexSpinner.selectedItem.toString()
        val weight = weightEditText.text.toString().toDoubleOrNull()
        val height = heightEditText.text.toString().toIntOrNull()

        if (age != null && weight != null && height != null) {
            lifecycleScope.launch {
                val userData = UserData(age = age, sex = sex, weight = weight, height = height, username = username)
                userProfileController.updateUserData(uidEmail!!, userData)
                toggleEditMode(false)
            }
        } else {
            // Obsłuż błąd, gdy wartości są niepoprawne
        }
    }
}
