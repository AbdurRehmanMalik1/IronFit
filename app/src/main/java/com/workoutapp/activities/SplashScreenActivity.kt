package com.workoutapp.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.workoutapp.R
import com.workoutapp.api.ApiProvider
import com.workoutapp.api.RetrofitInstance
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
        val api = ApiProvider.getInstance(this).api

        lifecycleScope.launch {
            delay(2000)
            try {
                val response = api.me()
                Log.d("SPLASH", "Response: ${response.code()} ${response.message()}")

                if (response.isSuccessful) {
                    startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
                    finish()
                    return@launch
                }
            } catch (e: Exception) {
                println(e)
            }
            val isOnboarded = AppPrefs.isOnboarded(this@SplashScreenActivity)
            val isFirstLaunch = AppPrefs.isFirstLaunch(this@SplashScreenActivity)

            if (isFirstLaunch || !isOnboarded) {
                startActivity(Intent(this@SplashScreenActivity, OnboardingActivity::class.java))
            } else {
                startActivity(Intent(this@SplashScreenActivity, LoginActivity::class.java))
            }
            finish()
        }
    }
}