package com.workoutapp.ui.custom

import android.app.Activity
import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.viewpager2.widget.ViewPager2
import com.workoutapp.R

class CustomBottomBar(
    private val activity: Activity,
    private val bottomBar: LinearLayout,
    private val viewPager: ViewPager2,
    private val tabs: List<Tab>
) {

    companion object class Tab(
        val icon: Int,
        val title: String
    )

    fun setup() {
        setupBottomBar()
        setupViewPagerSync()
    }

    private fun setupBottomBar() {

        tabs.forEachIndexed { index, tab ->

            val view = activity.layoutInflater.inflate(
                R.layout.bottom_tab_item,
                bottomBar,
                false
            )

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

    private fun setupViewPagerSync() {
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                selectTab(position)
            }
        })
    }

    private fun selectTab(position: Int) {

        for (i in 0 until bottomBar.childCount) {

            val view = bottomBar.getChildAt(i)
            val icon = view.findViewById<ImageView>(R.id.icon)
            val text = view.findViewById<TextView>(R.id.text)

            val isSelected = i == position
            view.isSelected = isSelected

            if (isSelected) {
                setWidth(view, 40)
                text.visibility = View.VISIBLE
                icon.setColorFilter(Color.WHITE)
            } else {
                setWidth(view, 0)
                text.visibility = View.GONE
                icon.setColorFilter(activity.getColor(R.color.disabled_tab_color))
            }
        }
    }

    private fun setWidth(view: View, dp: Int) {
        val params = view.layoutParams
        params.width = dpToPx(dp)
        view.layoutParams = params
    }

    private fun dpToPx(dp: Int): Int {
        return (dp * activity.resources.displayMetrics.density).toInt()
    }
}