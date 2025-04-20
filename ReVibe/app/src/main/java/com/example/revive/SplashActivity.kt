package com.example.revive

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.ImageView

class SplashActivity : AppCompatActivity() {

    private val splashTimeOut: Long = 3000 // 3 seconds

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val logoImageView: ImageView = findViewById(R.id.logoImageView)

        val fadeIn = AlphaAnimation(0f, 1f)
        fadeIn.duration = 1500
        val fadeOut = AlphaAnimation(1f, 0f)
        fadeOut.startOffset = 1500
        fadeOut.duration = 1500

        val animation = object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}
            override fun onAnimationEnd(animation: Animation?) {
                // After animation ends, start next activity
                checkFirstTimeUser()
            }
            override fun onAnimationRepeat(animation: Animation?) {}
        }

        fadeOut.setAnimationListener(animation)

        logoImageView.startAnimation(fadeIn)
        logoImageView.startAnimation(fadeOut)
    }

    private fun checkFirstTimeUser() {
        val sharedPref = getSharedPreferences("ReVibePrefs", MODE_PRIVATE)
        val isFirstTime = sharedPref.getBoolean("isFirstTime", true)
        if (isFirstTime) {
            val intent = Intent(this, OnboardingActivity::class.java)
            startActivity(intent)
        } else {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        finish()
    }
}
