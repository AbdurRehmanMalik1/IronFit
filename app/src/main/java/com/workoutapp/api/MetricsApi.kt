package com.workoutapp.api

import com.workoutapp.models.MetricDefinition
import com.workoutapp.models.api.request.SaveMetricsRequest
import com.workoutapp.models.api.response.MetricResponse
import com.workoutapp.models.api.response.SubscriptionResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface MetricsApi {

    @GET("metrics/snapshot")
    suspend fun getSnapshot(): List<MetricResponse>


    @GET("metrics/definitions")
    suspend fun getDefinitions(): List<MetricDefinition>

    @GET("metrics/subscriptions")
    suspend fun getSubscriptions(): List<SubscriptionResponse>

    @POST("metrics/subscriptions")
    suspend fun saveSubscriptions(
        @Body body: SaveMetricsRequest
    )
}