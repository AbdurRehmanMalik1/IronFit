package com.workoutapp.adapters


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.workoutapp.R
import com.workoutapp.models.WorkoutUI

class WorkoutAdapter(
    private val workouts: List<WorkoutUI>,
    private val onStartClick: (WorkoutUI) -> Unit
) : RecyclerView.Adapter<WorkoutAdapter.WorkoutViewHolder>() {

    class WorkoutViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.tvWorkoutName)
        val tvCalories: TextView = view.findViewById(R.id.tvWorkoutCalories)
        val tvDescription: TextView = view.findViewById(R.id.tvWorkoutDescription)
        val btnStart: Button = view.findViewById(R.id.btnStartWorkout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_item_workout_full, parent, false)
        return WorkoutViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: WorkoutViewHolder, position: Int) {
        val workout = workouts[position]

        holder.tvName.text = workout.name
        holder.tvDescription.text = workout.description ?: "No description"

        holder.tvCalories.text =
            "${workout.caloriesPerMinute} cal/min"

        holder.btnStart.setOnClickListener {
            onStartClick(workout)
        }
    }

    override fun getItemCount() = workouts.size
}