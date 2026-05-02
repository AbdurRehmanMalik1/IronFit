package com.workoutapp.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.workoutapp.R
import com.workoutapp.prefs.AppPrefs
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash_screen)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val isOnboarded = AppPrefs.isOnboarded(this)
        val isLoggedIn = AppPrefs.isLoggedIn(this)
        val isFirstLaunch = AppPrefs.isFirstLaunch(this)

        lifecycleScope.launch {
            delay(2000)
            if (isFirstLaunch || !isOnboarded) {
                startActivity(Intent(this@SplashScreenActivity, OnboardingActivity::class.java))
                finish()
            } else if (isLoggedIn) {
                startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
                finish()
            } else {
                startActivity(Intent(this@SplashScreenActivity, LoginActivity::class.java))
                finish()
            }
        }
    }
}