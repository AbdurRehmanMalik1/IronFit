package com.workoutapp.fragments

import MetricsViewModel
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.workoutapp.R
import com.workoutapp.activities.MetricsActivity
import com.workoutapp.adapters.MetricsAdapter
import com.workoutapp.adapters.MonthBarAdapter
import com.workoutapp.adapters.SuggestedWorkoutAdapter
import com.workoutapp.models.WorkoutUI


class HomeFragment : Fragment() {

    private val metricsViewModel: MetricsViewModel by viewModels()
    private lateinit var metricsAdapter: MetricsAdapter

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

        rvMetrics.layoutManager = GridLayoutManager(requireContext(), 2)

        metricsAdapter = MetricsAdapter(emptyList())
        rvMetrics.adapter = metricsAdapter


        val tvEmptyMetrics = view.findViewById<TextView>(R.id.tvEmptyMetrics)


        val tvSeeAllMetrics = view.findViewById<TextView>(R.id.tvSeeAllMetrics)

        val srlHomeFragment= view.findViewById<SwipeRefreshLayout>(R.id.srlHomeFragment)

        srlHomeFragment.setOnRefreshListener {
            metricsViewModel.loadSnapshot()
        }


        tvEmptyMetrics.setOnClickListener {
            startActivity(Intent(context, MetricsActivity::class.java))
        }

        tvSeeAllMetrics.setOnClickListener {
            startActivity(Intent(context, MetricsActivity::class.java))
        }

        rvMetrics.visibility = View.GONE
        tvEmptyMetrics.visibility = View.VISIBLE

        metricsViewModel.loadSnapshot()

        metricsViewModel.metrics.observe(viewLifecycleOwner) { data ->
            if (data.isNullOrEmpty()) {
                rvMetrics.visibility = View.GONE
                tvEmptyMetrics.visibility = View.VISIBLE
            } else {
                rvMetrics.visibility = View.VISIBLE
                tvEmptyMetrics.visibility = View.GONE
                metricsAdapter.updateData(data)
            }
            srlHomeFragment.isRefreshing = false
        }

        val workoutData = listOf(
            WorkoutUI("Running", "Burn fat and boost endurance with a steady run."),
            WorkoutUI("Biking", "Strengthen your legs and improve stamina, indoors or out.")
        )
        val rvSuggested = view.findViewById<RecyclerView>(R.id.rvSuggestWorkout)
        rvSuggested.adapter = SuggestedWorkoutAdapter(workoutData) { workout -> }


    }
}