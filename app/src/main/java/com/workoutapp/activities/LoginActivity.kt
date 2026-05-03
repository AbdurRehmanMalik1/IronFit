package com.workoutapp.activities

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.workoutapp.R
import com.workoutapp.api.AuthRepository
import com.workoutapp.prefs.AppPrefs
import kotlinx.coroutines.launch

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

        val repository = AuthRepository()

        btnLogin.setOnClickListener {

            val email = etEmail.text?.toString()?.trim().orEmpty()
            val password = etPassword.text?.toString().orEmpty()

            if (email.isBlank() || password.isBlank()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {
                try {

                    val response = repository.login(email, password)

                    if (response.isSuccessful) {

                        val body = response.body()

                        Toast.makeText(
                            this@LoginActivity,
                            body?.message ?: "Login successful",
                            Toast.LENGTH_SHORT
                        ).show()

                        AppPrefs.setLoggedIn(this@LoginActivity, true)
                        AppPrefs.saveCredentials(this@LoginActivity, email, password)
                        AppPrefs.saveUser(this@LoginActivity, body?.user)


                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        finishAffinity()

                    } else {
                        Toast.makeText(
                            this@LoginActivity,
                            "Login failed: ${response.errorBody()?.string()}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                } catch (e: Exception) {
                    Toast.makeText(
                        this@LoginActivity,
                        "Error: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        tvSignUp.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
            finish()
        }
    }
}
