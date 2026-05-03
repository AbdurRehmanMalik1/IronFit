package com.workoutapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment

import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.workoutapp.R
import com.workoutapp.activities.WorkoutActivity
import com.workoutapp.adapters.WorkoutAdapter
import com.workoutapp.api.ApiProvider
import com.workoutapp.api.WorkoutRepository
import com.workoutapp.models.WorkoutUI
import kotlinx.coroutines.launch

class WorkoutListFragment : Fragment(R.layout.fragment_workout_list) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.rvWorkouts)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        lifecycleScope.launch {
            val response = WorkoutRepository().getWorkoutTypes()

            val workoutList = response.map {
                WorkoutUI(
                    id = it.id,
                    name = it.name,
                    caloriesPerMinute = it.caloriesPerMinute,
                    description = "Burn calories with ${it.name}"
                )
            }

            recyclerView.adapter = WorkoutAdapter(workoutList) { workout ->
                (activity as WorkoutActivity).openWorkoutTimer(workout)
            }
        }
    }
}