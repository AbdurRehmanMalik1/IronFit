package com.workoutapp.models.api.response

import com.workoutapp.models.api.request.WorkoutType

data class WorkoutResponse(
    val id: String,
    val type: WorkoutType,
    val startTime: String,
    val endTime: String? = null,
    val durationMinutes: Int? = null,
    val caloriesBurned: Double? = null
)