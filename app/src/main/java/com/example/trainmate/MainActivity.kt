package com.example.trainmate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private var welcomeTextView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Pobierz intent, który uruchomił tę aktywność
        val intent = intent

        // Sprawdź, czy intent zawiera dodatkowe dane o nazwie "uID"
        val userID = intent.getStringExtra("uID")

        // Znajdź widok TextView o identyfikatorze "welcomeText"
        welcomeTextView = findViewById(R.id.welcomeText)

        // Ustaw tekst powitalny, wykorzystując wartość userID
        welcomeTextView?.text = "Welcome ${userID}!"
    }
}
