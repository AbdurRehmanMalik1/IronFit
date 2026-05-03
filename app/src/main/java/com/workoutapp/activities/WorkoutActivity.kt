package com.workoutapp.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.workoutapp.R
import com.workoutapp.api.WorkoutRepository
import com.workoutapp.fragments.WorkoutListFragment
import com.workoutapp.fragments.WorkoutTimerFragment
import com.workoutapp.models.WorkoutUI
import kotlinx.coroutines.launch

class WorkoutActivity : AppCompatActivity(), WorkoutTimerFragment.WorkoutTimerListener {


    private var isInitialized = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workout)

        checkActiveWorkout()
    }


    private fun checkActiveWorkout() {

        if (isInitialized) return
        isInitialized = true

        lifecycleScope.launch {
            try {
                val repo = WorkoutRepository()
                val activeWorkout = repo.getActiveWorkout()

                Log.d("ACTIVE_WORKOUT", "Got: $activeWorkout")

                if (activeWorkout != null && activeWorkout.id.isNotBlank()) {

                    supportFragmentManager.beginTransaction()
                        .replace(
                            R.id.fragmentContainer,
                            WorkoutTimerFragment.newInstance(
                                activeWorkout.id,
                                activeWorkout.type.name,
                                true
                            )
                        )
                        .commit()

                } else {

                    supportFragmentManager.beginTransaction()
                        .replace(
                            R.id.fragmentContainer,
                            WorkoutListFragment()
                        )
                        .commit()
                }

            } catch (e: Exception) {

                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, WorkoutListFragment())
                    .commit()
            }
        }
    }

    fun openWorkoutTimer(workout: WorkoutUI) {
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.fragmentContainer,
                WorkoutTimerFragment.newInstance(workout.id, workout.name, false)
            )
            .addToBackStack(null)
            .commit()
    }

    override fun onWorkoutEnded() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, WorkoutListFragment())
            .commit()
    }
}