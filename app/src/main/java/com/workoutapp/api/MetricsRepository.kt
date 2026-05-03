package com.workoutapp.api

import com.workoutapp.models.MetricDefinition
import com.workoutapp.models.MetricUI
import com.workoutapp.models.api.request.SaveMetricsRequest
import com.workoutapp.models.api.response.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MetricsRepository(
    private val api: MetricsApi
) {

    suspend fun getSnapshot(): List<MetricUI> {
        return withContext(Dispatchers.IO) {
            val response = api.getSnapshot()
            mapToUI(response)
        }
    }

    suspend fun getDefinitions(): List<MetricDefinition> {
        return withContext(Dispatchers.IO) {
            api.getDefinitions()
        }
    }

    suspend fun getSubscriptions(): List<SubscriptionResponse> {
        return withContext(Dispatchers.IO) {
            api.getSubscriptions()
        }
    }

    suspend fun saveSubscriptions(metrics: List<String>) {
        return withContext(Dispatchers.IO) {
            api.saveSubscriptions(SaveMetricsRequest(metrics))
        }
    }
}