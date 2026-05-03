package com.workoutapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.workoutapp.R
import com.workoutapp.adapters.ProgressMetricAdapter
import com.workoutapp.models.ProgressMetricUI
import androidx.core.graphics.toColorInt

class ProgressFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_progress, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val context = view.context

        val weeklyMetrics = listOf(
            ProgressMetricUI(
                title = "Steps",
                value = "4200",
                unit = "steps",
                iconRes = R.drawable.ic_run,
                iconColor = ContextCompat.getColor(context, R.color.primary_color)

            ),

            ProgressMetricUI(
                title = "Calories",
                value = "2.9",
                unit = "Kcal",
                iconRes = R.drawable.ic_fire,
                iconColor = "#FFB800".toColorInt()
            ),

            ProgressMetricUI(
                title = "Heart Rate",
                value = "76",
                unit = "Bpm",
                iconRes = R.drawable.ic_heart_rate,
                iconColor = "#8E5AF7".toColorInt()
            ),

            ProgressMetricUI(
                title = "Workout",
                value = "20m",
                unit = "Steady",
                iconRes = R.drawable.ic_time_workout,
                iconColor = "#32CD32".toColorInt()
            )
        )

        val rvMetrics = view.findViewById<RecyclerView>(R.id.rvProgressMetrics)
        rvMetrics.layoutManager = GridLayoutManager(requireContext(), 2)
        rvMetrics.adapter = ProgressMetricAdapter(weeklyMetrics)

        val progressPercent = 60
        val bannerTitle = view.findViewById<TextView>(R.id.tvBannerTitle)
        val bannerSubtitle = view.findViewById<TextView>(R.id.tvBannerSubtitle)
        val summaryTitle = view.findViewById<TextView>(R.id.tvWeeklySummaryTitle)
        val viewSummaryButton = view.findViewById<Button>(R.id.btnViewSummary)
        val progressBar = view.findViewById<ProgressBar>(R.id.progressWeeklyGoal)
        val progressText = view.findViewById<TextView>(R.id.tvProgressPercent)

        bannerTitle.text = getString(R.string.progress_banner_title)
        bannerSubtitle.text = getString(R.string.progress_banner_subtitle)
        summaryTitle.text = getString(R.string.progress_summary_title)
        viewSummaryButton.text = getString(R.string.progress_view_summary)

        progressBar.progress = progressPercent
        progressText.text = getString(R.string.progress_percent_format, progressPercent)
    }
}