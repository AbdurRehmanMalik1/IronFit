package com.workoutapp.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.ncorti.slidetoact.SlideToActView
import com.workoutapp.R
import com.workoutapp.prefs.AppPrefs
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class OnboardingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_onboarding_screen)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val welcomeSlider = findViewById<SlideToActView>(R.id.welcome_slider)

        welcomeSlider.onSlideCompleteListener = object : SlideToActView.OnSlideCompleteListener {
            override fun onSlideComplete(view: SlideToActView) {
                lifecycleScope.launch {
                    delay(700)
                    AppPrefs.setOnboarded(this@OnboardingActivity, true)
                    AppPrefs.setFirstLaunch(this@OnboardingActivity, false)
                    startActivity(Intent(this@OnboardingActivity, SignUpActivity::class.java))
                    finish()
                }
            }
        }

    }
}