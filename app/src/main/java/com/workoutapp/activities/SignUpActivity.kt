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
import com.workoutapp.logger.ApiLogger
import com.workoutapp.prefs.AppPrefs
import kotlinx.coroutines.launch

class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_up)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val etName = findViewById<TextInputEditText>(R.id.etSignUpName)
        val etEmail = findViewById<TextInputEditText>(R.id.etSignUpEmail)
        val etPassword = findViewById<TextInputEditText>(R.id.etSignUpPassword)
        val etConfirmPassword = findViewById<TextInputEditText>(R.id.etSignUpConfirmPassword)
        val btnSignUp = findViewById<MaterialButton>(R.id.btnSignUp)
        val tvLogin = findViewById<TextView>(R.id.tvGoToLogin)

        val repository = AuthRepository(this)

        btnSignUp.setOnClickListener {

            val name = etName.text?.toString()?.trim().orEmpty()
            val email = etEmail.text?.toString()?.trim().orEmpty()
            val password = etPassword.text?.toString().orEmpty()
            val confirmPassword = etConfirmPassword.text?.toString().orEmpty()

            if (name.isBlank() || email.isBlank() || password.isBlank() || confirmPassword.isBlank()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            lifecycleScope.launch {
                try {
                    val response = repository.signup(name, email, password)
                    if (response.isSuccessful) {

                        ApiLogger.logSuccess(
                            this@SignUpActivity,
                            "POST /auth/signup",
                            response
                        )

                        startActivity(Intent(this@SignUpActivity, TermsActivity::class.java))
                        finish()

                    } else {
                        ApiLogger.logError(
                            this@SignUpActivity,
                            "POST /auth/signup",
                            response
                        )
                    }
                } catch (e: Exception) {
                    ApiLogger.logException(
                        this@SignUpActivity,
                        "POST /auth/signup",
                        e
                    )
                }
            }
        }

        tvLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}
