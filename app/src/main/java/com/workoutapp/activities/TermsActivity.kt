package com.workoutapp.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ScrollView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.checkbox.MaterialCheckBox
import com.workoutapp.R
import com.workoutapp.prefs.AppPrefs

class TermsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_terms)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val checkboxAgree = findViewById<MaterialCheckBox>(R.id.checkboxAgree)
        val svTerms = findViewById<ScrollView>(R.id.svTerms)
        val btnAgree = findViewById<MaterialButton>(R.id.btnAgree)
        val btnScroll = findViewById<MaterialButton>(R.id.btnScroll)

        val scrollToTopString = getString(R.string.scroll_to_top)
        val scrollToBottomString = getString(R.string.scroll_to_bottom)
        var isAtBottom = false

        checkboxAgree.setOnCheckedChangeListener { _, checked ->
            btnAgree.isEnabled = checked
        }
        btnAgree.setOnClickListener {
            AppPrefs.setTermsAccepted(this, true)
            startActivity(Intent(this@TermsActivity, LoginActivity::class.java))
            finish()
        }


        btnScroll.setOnClickListener {
            if (isAtBottom) {
                svTerms.fullScroll(View.FOCUS_UP)
            } else {
                svTerms.fullScroll(View.FOCUS_DOWN)
            }
        }

        svTerms.viewTreeObserver.addOnScrollChangedListener {

            val isNowAtBottom = !svTerms.canScrollVertically(1)

            if (isNowAtBottom != isAtBottom) {
                isAtBottom = isNowAtBottom

                btnScroll.text = if (isAtBottom) {
                    scrollToTopString
                } else {
                    scrollToBottomString
                }
            }
        }
    }
}