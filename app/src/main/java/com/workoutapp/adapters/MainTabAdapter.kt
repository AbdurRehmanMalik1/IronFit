package com.workoutapp.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.workoutapp.fragments.CalendarFragment
import com.workoutapp.fragments.HistoryFragment
import com.workoutapp.fragments.HomeFragment
import com.workoutapp.fragments.ProfileFragment
import com.workoutapp.fragments.ProgressFragment

class MainTabAdapter : FragmentStateAdapter {

    constructor(activity: FragmentActivity) : super(activity) {
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> HomeFragment()
            1 -> HistoryFragment()
            2 -> ProgressFragment()
            3 -> CalendarFragment()
            4 -> ProfileFragment()
            else -> HomeFragment()
        }
    }

    override fun getItemCount(): Int {
        return 5
    }
}