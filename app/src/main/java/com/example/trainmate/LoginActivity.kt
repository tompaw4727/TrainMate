package com.example.trainmate

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    private var loginButton: Button? = null
    private var username: EditText? = null
    private var password: EditText? = null
    private var forgotPassword: TextView? = null
    private var register: TextView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        username = findViewById(R.id.username_input)
        password = findViewById(R.id.password_input)

        loginButton = findViewById(R.id.login_btn)
        forgotPassword = findViewById(R.id.forgot_password_btn)
        register = findViewById(R.id.register_btn)

    }
}