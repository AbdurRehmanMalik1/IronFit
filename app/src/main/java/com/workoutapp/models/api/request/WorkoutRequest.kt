package com.workoutapp.models.api.request

data class WorkoutType(
    val id: String,
    val name: String,
    val caloriesPerMinute: Double
)

data class StartWorkoutRequest(
    val typeId: String
)
