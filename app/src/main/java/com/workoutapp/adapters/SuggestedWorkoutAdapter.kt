package com.workoutapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.workoutapp.R
import com.workoutapp.models.WorkoutUI

class SuggestedWorkoutAdapter(
    private val workouts: List<WorkoutUI>,
    private val onStartClick: (WorkoutUI) -> Unit
) : RecyclerView.Adapter<SuggestedWorkoutAdapter.WorkoutViewHolder>() {

    class WorkoutViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTitle: TextView = view.findViewById(R.id.tvWorkoutTitle)
        val tvDescription: TextView = view.findViewById(R.id.tvWorkoutDescription)
        val btnStart: Button = view.findViewById(R.id.btnStartWorkout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_suggest_workout_item, parent, false)
        return WorkoutViewHolder(view)
    }

    override fun onBindViewHolder(holder: WorkoutViewHolder, position: Int) {
        val workout = workouts[position]
        holder.tvTitle.text = workout.title
        holder.tvDescription.text = workout.description

        holder.btnStart.setOnClickListener { onStartClick(workout) }
    }

    override fun getItemCount() = workouts.size
}