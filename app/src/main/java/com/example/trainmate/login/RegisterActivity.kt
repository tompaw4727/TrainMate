package com.example.trainmate.login

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.lifecycleScope
import com.example.trainmate.BaseActivity
import com.example.trainmate.R
import com.example.trainmate.controller.UserProfileController
import com.example.trainmate.entity.UserData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

class RegisterActivity : BaseActivity() {
    private val userProfileController = UserProfileController()
    private var registerButton: Button? = null
    private var inputEmail: EditText? = null
    private var inputUsername: EditText? = null
    private var inputPassword: EditText? = null
    private var inputConfPasssword: EditText? = null

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_activity)

        registerButton = findViewById(R.id.register_btn)
        inputEmail = findViewById(R.id.email_input_r)
        inputUsername = findViewById(R.id.username_input_r)
        inputPassword = findViewById(R.id.password_input_r)
        inputConfPasssword = findViewById(R.id.confirm_password_input_r)

        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        registerButton?.setOnClickListener {
            registerUser()
        }
    }

    private fun validateRegisterDetails(): Boolean {
        return when {
            TextUtils.isEmpty(inputEmail?.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_email), true)
                false
            }

            TextUtils.isEmpty(inputUsername?.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_name), true)
                false
            }

            TextUtils.isEmpty(inputPassword?.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_password), true)
                false
            }

            TextUtils.isEmpty(inputConfPasssword?.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_reppassword), true)
                false
            }

            inputPassword?.text.toString().trim { it <= ' ' } != inputConfPasssword?.text.toString()
                .trim { it <= ' ' } -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_password_mismatch), true)
                false
            }

            else -> true
        }
    }

    fun goToLogin(view: View) {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun registerUser() {
        if (validateRegisterDetails()) {
            val email: String = inputEmail?.text.toString().trim { it <= ' ' }
            val password: String = inputPassword?.text.toString().trim { it <= ' ' }

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val firebaseUser: FirebaseUser? = auth.currentUser
                        val userId = firebaseUser?.uid
                        val userEmail = firebaseUser?.email

                        if (userId != null && userEmail != null) {
                            val username = inputUsername?.text.toString().trim { it <= ' ' }
                            val userData = UserData(username = username)

                            lifecycleScope.launch {
                                try {
                                    userProfileController.postUserData(userEmail, userData)

                                    showErrorSnackBar(
                                        "You are registered successfully. Your user id is $userId",
                                        false
                                    )
                                    auth.signOut()
                                    finish()
                                } catch (e: Exception) {
                                    showErrorSnackBar(e.message.toString(), true)
                                }
                            }
                        } else {
                            showErrorSnackBar("Failed to retrieve user information.", true)
                        }
                    } else {
                        showErrorSnackBar(task.exception?.message ?: "Unknown error occurred.", true)
                    }
                }
        }
    }

}
