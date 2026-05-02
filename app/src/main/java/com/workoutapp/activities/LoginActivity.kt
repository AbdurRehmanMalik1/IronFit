package com.workoutapp.activities

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.workoutapp.R
import com.workoutapp.prefs.AppPrefs

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val etEmail = findViewById<TextInputEditText>(R.id.etLoginEmail)
        val etPassword = findViewById<TextInputEditText>(R.id.etLoginPassword)
        val btnLogin = findViewById<MaterialButton>(R.id.btnLogin)
        val tvSignUp = findViewById<TextView>(R.id.tvGoToSignUp)

        val savedEmail = AppPrefs.getSavedEmail(this)
        if (savedEmail.isNotBlank()) {
            etEmail.setText(savedEmail)
        }

        btnLogin.setOnClickListener {
            val email = etEmail.text?.toString()?.trim().orEmpty()
            val password = etPassword.text?.toString().orEmpty()

            if (email.isBlank() || password.isBlank()) {
                Toast.makeText(this, getString(R.string.please_fill_all_fields), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (AppPrefs.getSavedEmail(this).isBlank()) {
                Toast.makeText(this, getString(R.string.please_sign_up_first), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!AppPrefs.isTermsAccepted(this)) {
                Toast.makeText(this, getString(R.string.terms_not_accepted), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!AppPrefs.isValidCredentials(this, email, password)) {
                Toast.makeText(this, getString(R.string.invalid_login_credentials), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            AppPrefs.setLoggedIn(this, true)
            startActivity(Intent(this, MainActivity::class.java))
            finishAffinity()
        }

        tvSignUp.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
            finish()
        }
    }
}
