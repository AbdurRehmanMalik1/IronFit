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

        btnSignUp.setOnClickListener {
            val name = etName.text?.toString()?.trim().orEmpty()
            val email = etEmail.text?.toString()?.trim().orEmpty()
            val password = etPassword.text?.toString().orEmpty()
            val confirmPassword = etConfirmPassword.text?.toString().orEmpty()

            if (name.isBlank() || email.isBlank() || password.isBlank() || confirmPassword.isBlank()) {
                Toast.makeText(this, getString(R.string.please_fill_all_fields), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                Toast.makeText(this, getString(R.string.passwords_do_not_match), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            AppPrefs.saveCredentials(this, email, password)
            AppPrefs.setLoggedIn(this, false)
            AppPrefs.setTermsAccepted(this, false)

            Toast.makeText(this, getString(R.string.account_created_complete_terms), Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, TermsActivity::class.java))
            finish()
        }

        tvLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}
