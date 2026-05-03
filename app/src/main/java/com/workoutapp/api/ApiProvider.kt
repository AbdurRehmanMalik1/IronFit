package com.workoutapp.api

import android.content.Context

object ApiProvider {

    private lateinit var instance: RetrofitInstance

    fun init(context: Context) {
        instance = RetrofitInstance(context)
    }

    fun authApi(): AuthApi = instance.authApi

    fun metricsApi(): MetricsApi = instance.metricsApi

    fun workoutApi(): WorkoutApi = instance.workoutApi
}