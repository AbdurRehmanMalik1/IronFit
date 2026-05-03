package com.workoutapp.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.workoutapp.R


import android.view.View
import android.widget.ScrollView

import com.google.android.material.button.MaterialButton

class ReadableTermsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_readable_terms)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val svTerms = findViewById<ScrollView>(R.id.svTerms)
        val btnScroll = findViewById<MaterialButton>(R.id.btnScroll)

        val scrollToTopString = getString(R.string.scroll_to_top)
        val scrollToBottomString = getString(R.string.scroll_to_bottom)

        var isAtBottom = false

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