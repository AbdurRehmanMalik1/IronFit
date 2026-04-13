package com.workoutapp

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.workoutapp.adapters.MainTabAdapter
import com.workoutapp.models.BottomTab

class MainActivity : AppCompatActivity() {

    private lateinit var bottomBar: LinearLayout
    private lateinit var viewPager: ViewPager2

    private val tabs = listOf(
        BottomTab(R.drawable.ic_home, "Workout"),
        BottomTab(R.drawable.ic_history, "History"),
        BottomTab(R.drawable.ic_progress, "Progress"),
        BottomTab(R.drawable.ic_calendar, "Calendar"),
        BottomTab(R.drawable.ic_account_circle, "Profile")
    )

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


        setupBottomBar()
        setupViewPagerSync()

        //        initializeTabLayout()
    }
    private fun setupViewPagerSync() {
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                selectTab(position)
            }
        })
    }
    private fun setupBottomBar() {

        tabs.forEachIndexed { index, tab ->

            val view = layoutInflater.inflate(R.layout.bottom_tab_item, bottomBar, false)

            val icon = view.findViewById<ImageView>(R.id.icon)
            val text = view.findViewById<TextView>(R.id.text)

            icon.setImageResource(tab.icon)
            text.text = tab.title

            view.setOnClickListener {
                viewPager.currentItem = index
                selectTab(index)
            }

            bottomBar.addView(view)
        }

        selectTab(0)
    }
    private fun selectTab(position: Int) {

        for (i in 0 until bottomBar.childCount) {

            val view = bottomBar.getChildAt(i)
            val icon = view.findViewById<ImageView>(R.id.icon)
            val text = view.findViewById<TextView>(R.id.text)

            val isSelected = i == position

            view.isSelected = isSelected

            if (isSelected) {
                view.layoutParams.width = dpToPx(78)
                text.visibility = View.VISIBLE

//                view.animate()
//                    .scaleX(1.1f)
//                    .scaleY(1.1f)
//                    .setDuration(200)
//                    .start()

                icon.setColorFilter(Color.WHITE)
            } else {
                view.layoutParams.width = dpToPx(0)
                text.visibility = View.GONE
                icon.setColorFilter(getColor(R.color.disabled_tab_color))
            }
        }
    }
    fun dpToPx(dp: Int): Int {
        return (dp * resources.displayMetrics.density).toInt()
    }

//    @SuppressLint("InflateParams")
//    private fun initializeTabLayout() {
//        TabLayoutMediator(bottomBar, viewPager) { tab, position ->
//            val view = layoutInflater.inflate(R.layout.tab_item, null, false)
//            val icon = view.findViewById<ImageView>(R.id.tabIcon)
//            val text = view.findViewById<TextView>(R.id.tabText)
//            when (position) {
//                0 -> {
//                    icon.setImageResource(R.drawable.ic_home)
//                    text.text = resources.getString(R.string.workout)
//                    icon.visibility = View.VISIBLE
//                }
//
//                1 -> {
//                    icon.setImageResource(R.drawable.ic_history)
//                    text.text = resources.getString(R.string.history)
//                }
//
//                2 -> {
//                    icon.setImageResource(R.drawable.ic_progress)
//                    text.text = resources.getString(R.string.progress)
//                }
//
//                3 -> {
//                    icon.setImageResource(R.drawable.ic_calendar)
//                    text.text = resources.getString(R.string.calendar)
//                }
//
//                4 -> {
//                    icon.setImageResource(R.drawable.ic_account_circle)
//                    text.text = resources.getString(R.string.profile)
//                }
//            }
//            tab.customView = view
//        }.attach()
//        bottomBar.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
//
//            override fun onTabSelected(tab: TabLayout.Tab) {
//                val view = tab.customView ?: return
//
//                val icon = view.findViewById<ImageView>(R.id.tabIcon)
//                val text = view.findViewById<TextView>(R.id.tabText)
//
//                icon.setColorFilter(
//                    ContextCompat.getColor(this@MainActivity, R.color.white)
//                )
//
//                text.visibility = View.VISIBLE
//                text.alpha = 1f
//            }
//
//            override fun onTabUnselected(tab: TabLayout.Tab) {
//                val view = tab.customView ?: return
//
//                val icon = view.findViewById<ImageView>(R.id.tabIcon)
//
//                icon.setColorFilter(
//                    ContextCompat.getColor(this@MainActivity, R.color.disabled_tab_color)
//                )
//
//                val text = view.findViewById<TextView>(R.id.tabText)
//                text.visibility = View.VISIBLE
//                text.alpha = 0f
//
//            }
//
//            override fun onTabReselected(tab: TabLayout.Tab) {}
//        })
//    }
}
