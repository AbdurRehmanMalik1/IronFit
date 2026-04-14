package com.workoutapp.activities

import android.os.Bundle
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.workoutapp.R
import com.workoutapp.adapters.MainTabAdapter
import com.workoutapp.models.BottomTab
import com.workoutapp.ui.custom.CustomBottomBar

class MainActivity : AppCompatActivity() {

    private lateinit var bottomBar: LinearLayout
    private lateinit var viewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        bottomBar = findViewById(R.id.bottomBar)
        viewPager = findViewById(R.id.viewPager)

        viewPager.adapter = MainTabAdapter(this@MainActivity)

        val tabs = listOf(
            BottomTab(R.drawable.ic_home, "Workout"),
            BottomTab(R.drawable.ic_history, "History"),
            BottomTab(R.drawable.ic_progress, "Progress"),
            BottomTab(R.drawable.ic_calendar, "Calendar"),
            BottomTab(R.drawable.ic_account_circle, "Profile")
        )
        CustomBottomBar(this, bottomBar, viewPager, tabs).setup()
    }
}