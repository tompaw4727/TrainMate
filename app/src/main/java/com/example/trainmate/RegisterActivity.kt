package com.example.trainmate

import androidx.appcompat.app.AppCompatActivity



import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class RegisterActivity : BaseActivity() {

    private var registerButton: Button? = null
    private var inputEmail: EditText? = null
    private var inputUsername: EditText? = null
    private var inputPassword: EditText? = null
    private var inputConfPasssword: EditText? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        registerButton = findViewById(R.id.register_btn)
        inputEmail = findViewById(R.id.email_input_r)
        inputUsername = findViewById(R.id.username_input_r)
        inputPassword = findViewById(R.id.password_input_r)
        inputConfPasssword = findViewById(R.id.confirm_password_input_r)

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

    // Przejście do aktywności logowania
    fun goToLogin(view: View) {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    // Rejestracja użytkownika
    private fun registerUser() {
        if (validateRegisterDetails()) {
            val login: String = inputEmail?.text.toString().trim() { it <= ' ' }
            val password: String = inputPassword?.text.toString().trim() { it <= ' ' }

            // Utworzenie użytkownika w FirebaseAuth
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(login, password)
                .addOnCompleteListener(
                    OnCompleteListener<AuthResult> { task ->
                        if (task.isSuccessful) {
                            val firebaseUser: FirebaseUser = task.result!!.user!!
                            showErrorSnackBar(
                                "You are registered successfully. Your user id is ${firebaseUser.uid}",
                                false
                            )

                            // Wylogowanie użytkownika i zakończenie aktywności
                            FirebaseAuth.getInstance().signOut()
                            finish()
                        } else {
                            showErrorSnackBar(task.exception!!.message.toString(), true)
                        }
                    }
                )
        }
    }

}