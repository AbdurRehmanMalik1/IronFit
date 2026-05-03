package com.workoutapp.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.button.MaterialButton
import com.workoutapp.R
import com.workoutapp.api.WorkoutRepository
import com.workoutapp.prefs.WorkoutSessionStore
import kotlinx.coroutines.launch

class WorkoutTimerFragment : Fragment(R.layout.fragment_workout_timer) {


    interface WorkoutTimerListener {
        fun onWorkoutEnded()
    }

    private var sessionId: String? = null

    private var listener: WorkoutTimerListener? = null

    private var workoutId: String? = null

    private var isResume = false

    private var isRunning = false
    private var elapsedSeconds = 0
    private val handler = Handler(Looper.getMainLooper())
    private var timerRunnable: Runnable? = null
    private var workoutName: String? = null

    private var startTime: Long = 0


    override fun onAttach(context: android.content.Context) {
        super.onAttach(context)

        listener = context as? WorkoutTimerListener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        workoutId = arguments?.getString("id")
        workoutName = arguments?.getString("name")
        isResume = arguments?.getBoolean("resume") ?: false
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tvTimer = view.findViewById<TextView>(R.id.tvTimer)

        val session = WorkoutSessionStore.get(requireContext())

        val tvWorkoutTimerName = view.findViewById<TextView>(R.id.tvWorkoutTimerName)

        if (!isResume) {
            startWorkout()
        }

        tvWorkoutTimerName.text = workoutName


        if (session != null && session.id == workoutId) {
            startTime = session.startTime
            isRunning = session.running

            elapsedSeconds =
                ((System.currentTimeMillis() - startTime) / 1000).toInt()

        } else {

            // 🔥 NEW SESSION
            startTime = System.currentTimeMillis()
            elapsedSeconds = 0
            isRunning = false
        }

        startTimer(tvTimer)



        view.findViewById<MaterialButton>(R.id.btnStartPause).setOnClickListener {
            isRunning = !isRunning
            if (!isRunning) {
                startTime = System.currentTimeMillis() - (elapsedSeconds * 1000L)
            }
            WorkoutSessionStore.save(
                requireContext(),
                workoutId ?: "",
                startTime,
                elapsedSeconds,
                isRunning
            )
        }

        view.findViewById<MaterialButton>(R.id.btnEnd).setOnClickListener {

            isRunning = false
            elapsedSeconds = 0

            val id = sessionId ?: workoutId

            if (id == null) {
                Toast.makeText(requireContext(), "No active workout", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {
                try {
                    WorkoutRepository().endWorkout(id)

                    WorkoutSessionStore.clear(requireContext())

                    Toast.makeText(
                        requireContext(),
                        "Workout Ended",
                        Toast.LENGTH_SHORT
                    ).show()

                    listener?.onWorkoutEnded()

                } catch (e: Exception) {

                    Toast.makeText(
                        requireContext(),
                        "Failed to end workout",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
    // ⏱ TIMER
    private fun startTimer(tvTimer: TextView) {

        timerRunnable = object : Runnable {
            @SuppressLint("DefaultLocale")
            override fun run() {

                if (isRunning) {
                    elapsedSeconds = ((System.currentTimeMillis() - startTime) / 1000).toInt()

                    val hours = elapsedSeconds / 3600
                    val minutes = (elapsedSeconds % 3600) / 60
                    val seconds = elapsedSeconds % 60

                    tvTimer.text = String.format("%02d:%02d:%02d", hours, minutes, seconds)
                }

                handler.postDelayed(this, 1000)
            }
        }

        handler.post(timerRunnable!!)
    }

    private fun startWorkout() {
        val id = workoutId ?: return

        lifecycleScope.launch {
            try {
                val response = WorkoutRepository().startWorkout(id)

                sessionId = response.id  // 🔥 THIS IS CRITICAL

                Toast.makeText(
                    requireContext(),
                    "Workout Started!",
                    Toast.LENGTH_SHORT
                ).show()

            } catch (e: Exception) {
                Toast.makeText(
                    requireContext(),
                    "Failed to start workout",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    companion object {

        fun newInstance(
            workoutId: String,
            workoutName: String,
            isResume: Boolean = false
        ) = WorkoutTimerFragment().apply {
            arguments = Bundle().apply {
                putString("id", workoutId)
                putString("name", workoutName)
                putBoolean("resume", isResume)
            }
        }
    }
}