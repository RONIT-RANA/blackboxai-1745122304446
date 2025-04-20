package com.example.revive

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class OnboardingActivity : AppCompatActivity() {

    private lateinit var nameEditText: EditText
    private lateinit var ageEditText: EditText
    private lateinit var continueButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)

        nameEditText = findViewById(R.id.editTextName)
        ageEditText = findViewById(R.id.editTextAge)
        continueButton = findViewById(R.id.buttonContinue)

        continueButton.setOnClickListener {
            val name = nameEditText.text.toString().trim()
            val ageStr = ageEditText.text.toString().trim()

            if (name.isEmpty()) {
                Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (ageStr.isEmpty()) {
                Toast.makeText(this, "Please enter your age", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val age = ageStr.toIntOrNull()
            if (age == null || age <= 0) {
                Toast.makeText(this, "Please enter a valid age", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val sharedPref = getSharedPreferences("ReVibePrefs", MODE_PRIVATE)
            with(sharedPref.edit()) {
                putString("userName", name)
                putInt("userAge", age)
                putBoolean("isFirstTime", false)
                apply()
            }

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
