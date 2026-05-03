package com.workoutapp.models

data class WorkoutUI(
    val id: String,
    val name: String,
    val caloriesPerMinute: Double,
    val description: String? = null
)