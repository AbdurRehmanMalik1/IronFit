package com.workoutapp.api

import okhttp3.internal.concurrent.Task
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface CalenderApi {
    @GET("/tasks")
    fun getTasksByDate(
        @Query("date") date: String
    ): Call<List<Task>>

    @POST("/tasks")
    fun createTask(
        @Body task: Task
    ): Call<Task>
}