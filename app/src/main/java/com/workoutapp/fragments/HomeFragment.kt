package com.workoutapp.fragments

import MetricsViewModel
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.workoutapp.R
import com.workoutapp.activities.MetricsActivity
import com.workoutapp.activities.WorkoutActivity
import com.workoutapp.adapters.MetricsAdapter
import com.workoutapp.adapters.MonthBarAdapter
import com.workoutapp.adapters.SuggestedWorkoutAdapter
import com.workoutapp.api.WorkoutRepository
import com.workoutapp.models.WorkoutUI
import com.workoutapp.prefs.AppPrefs
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {

    private val metricsViewModel: MetricsViewModel by viewModels()
    private lateinit var metricsAdapter: MetricsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val context = requireContext()

        val tvGreetUser = view.findViewById<TextView>(R.id.tvGreetUser)

        val user = AppPrefs.getUser(requireContext())

        tvGreetUser.text = if (user != null) {
            "Hello, ${user.name}"
        } else {
            "Hello"
        }

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

        val tvSeeAllWorkout = view.findViewById<TextView>(R.id.tvSeeAllWorkout)

        tvSeeAllWorkout.setOnClickListener {
            context.startActivity( Intent(context, WorkoutActivity::class.java))
        }

        lifecycleScope.launch {

            val repo = WorkoutRepository()

            val top = repo.getTopWorkouts(3)

            val workoutData = top.map {
                WorkoutUI(
                    id = it.type.id,
                    name = it.type.name,
                    caloriesPerMinute = it.type.caloriesPerMinute ?: 0.0,
                    description = "Most frequently done workout"
                )
            }

            val rvSuggested = view.findViewById<RecyclerView>(R.id.rvSuggestWorkout)
            rvSuggested.adapter = SuggestedWorkoutAdapter(workoutData) { workout ->
                lifecycleScope.launch {
                    try {
                        repo.startWorkout(workout.id)
                        val intent = Intent(requireContext(), WorkoutActivity::class.java)
                        startActivity(intent)
                    } catch (_: Exception) {
                        Toast.makeText(
                            requireContext(),
                            "Failed to start workout",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

    }
}