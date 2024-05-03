package com.example.trainmate

import android.content.Intent
import android.os.Bundle

import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : BaseActivity(), View.OnClickListener {

    private var inputEmail: EditText? = null
    private var inputPassword: EditText? = null
    private var loginButton: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        inputEmail = findViewById(R.id.email_input_l)
        inputPassword = findViewById(R.id.password_input_l)
        loginButton = findViewById(R.id.login_btn)

        loginButton?.setOnClickListener() {
            logInRegisteredUser()
        }
    }

    override fun onClick(view: View?) {
        if (view != null) {
            when (view.id) {
                // Jeśli kliknięto registerTextViewClickable (przycisk przejścia do rejestracji), uruchom aktywność rejestracji
                // aby TextView mogł być klikalny,nalezy ustawić właściwą funkcję w pliku xml.
                R.id.register_txt_clickable -> {
                    val intent = Intent(this, RegisterActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }

    // Walidacja danych logowania
    private fun validateLoginDetails(): Boolean {
        return when {
            TextUtils.isEmpty(inputEmail?.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_email), true)
                false
            }
            TextUtils.isEmpty(inputPassword?.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_password), true)
                false
            }
            else -> {
                showErrorSnackBar("Your details are valid", false)
                true
            }
        }
    }

    // Logowanie zarejestrowanego użytkownika
    private fun logInRegisteredUser() {
        if (validateLoginDetails()) {
            val email = inputEmail?.text.toString().trim() { it <= ' ' }
            val password = inputPassword?.text.toString().trim() { it <= ' ' }

            // Logowanie za pomocą FirebaseAuth
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        showErrorSnackBar(resources.getString(R.string.login_successfull), false) // text zdefiniowany w res -> values -> strings.xml
                        goToMainActivity()
                        finish()
                    } else {
                        showErrorSnackBar(task.exception!!.message.toString(), true)
                    }
                }
        }
    }

    // Przejście do aktywności głównej
    open fun goToMainActivity() {
        val user = FirebaseAuth.getInstance().currentUser
        val uid = user?.email.toString()

        //Przekazanie wartości uid
        val intent = Intent(this, HomePageActivity::class.java)
        intent.putExtra("uID", uid)
        startActivity(intent)
    }
}
