package com.workoutapp.api

import com.workoutapp.models.api.request.StartWorkoutRequest
import com.workoutapp.models.api.response.WorkoutResponse
import com.workoutapp.models.api.request.WorkoutType
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface WorkoutApi {

    @GET("workouts/types")
    suspend fun getWorkoutTypes(): List<WorkoutType>

    @GET("workouts/active")
    suspend fun getActiveWorkout(): WorkoutResponse?

    @POST("workouts/start")
    suspend fun startWorkout(
        @Body request: StartWorkoutRequest
    ): WorkoutResponse

    @POST("workouts/end/{id}")
    suspend fun endWorkout(
        @Path("id") workoutId: String
    ): WorkoutResponse

    @GET("workouts/top/{limit}")
    suspend fun getTopWorkouts(
        @Path("limit") limit: Int
    ): List<WorkoutResponse>
}