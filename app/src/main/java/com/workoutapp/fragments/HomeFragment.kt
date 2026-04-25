package com.workoutapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.workoutapp.R
import com.workoutapp.adapters.MetricsAdapter
import com.workoutapp.adapters.MonthBarAdapter
import com.workoutapp.adapters.SuggestedWorkoutAdapter
import com.workoutapp.models.MetricUI
import com.workoutapp.models.WorkoutUI

class HomeFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val context = requireContext()
        val monthChart = view.findViewById<RecyclerView>(R.id.rvMonthChart)

        monthChart.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val monthlyData = listOf(
            60, 85, 45, 70, 80, 35,
            65, 68, 82, 55, 78, 40
        )
        monthChart.adapter = MonthBarAdapter(monthlyData)


        val rvMetrics: RecyclerView = view.findViewById(R.id.rvMetricsGrid)
        rvMetrics.layoutManager = FlexboxLayoutManager(context).apply {
            flexDirection = FlexDirection.ROW
            flexWrap = FlexWrap.NOWRAP
            justifyContent = JustifyContent.FLEX_START
        }
        val metricUIData = listOf(
            MetricUI("Water", "2.9", "Liters", R.drawable.ic_water_drop, "#1D1B67"),
            MetricUI("Calories", "2.9", "Cal", R.drawable.ic_fire, "#FFB800"),
            MetricUI("Heart Rate", "76", "Bpm", R.drawable.ic_heart_rate, "#8E5AF7")
        )
        rvMetrics.adapter = MetricsAdapter(metricUIData)

        val workoutData = listOf(
            WorkoutUI("Running", "Burn fat and boost endurance with a steady run."),
            WorkoutUI("Biking", "Strengthen your legs and improve stamina, indoors or out.")
        )
        val rvSuggested: RecyclerView = view.findViewById(R.id.rvSuggestWorkout) // Ensure this ID matches your XML
        rvSuggested.layoutManager = LinearLayoutManager(context)
        rvSuggested.adapter = SuggestedWorkoutAdapter(workoutData) { workout -> }


    }
}