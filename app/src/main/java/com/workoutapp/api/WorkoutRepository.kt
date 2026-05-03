package com.workoutapp.api

import com.workoutapp.models.api.request.StartWorkoutRequest
import com.workoutapp.models.api.response.WorkoutResponse
import com.workoutapp.models.api.request.WorkoutType

class WorkoutRepository{
    private val api: WorkoutApi = ApiProvider.workoutApi()
    suspend fun getWorkoutTypes(): List<WorkoutType> {
        return api.getWorkoutTypes()
    }

    suspend fun getActiveWorkout(): WorkoutResponse? {
        return api.getActiveWorkout()
    }

    suspend fun startWorkout(typeId: String): WorkoutResponse {
        return api.startWorkout(StartWorkoutRequest(typeId))
    }

    suspend fun endWorkout(workoutId: String): WorkoutResponse {
        return api.endWorkout(workoutId)
    }
    suspend fun getTopWorkouts(limit: Int): List<WorkoutResponse> {
        return api.getTopWorkouts(limit)
    }
}