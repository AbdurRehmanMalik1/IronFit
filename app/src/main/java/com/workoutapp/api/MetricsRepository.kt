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

    // ----------------------------------
    // 📊 SNAPSHOT (HOME SCREEN)
    // ----------------------------------
    suspend fun getSnapshot(): List<MetricUI> {
        return withContext(Dispatchers.IO) {
            val response = api.getSnapshot()
            mapToUI(response)
        }
    }

    // ----------------------------------
    // 📥 METRIC DEFINITIONS
    // ----------------------------------
    suspend fun getDefinitions(): List<MetricDefinition> {
        return withContext(Dispatchers.IO) {
            api.getDefinitions()
        }
    }

    // ----------------------------------
    // 📥 USER SUBSCRIPTIONS
    // ----------------------------------
    suspend fun getSubscriptions(): List<SubscriptionResponse> {
        return withContext(Dispatchers.IO) {
            api.getSubscriptions()
        }
    }

    // ----------------------------------
    // 💾 SAVE SUBSCRIPTIONS
    // ----------------------------------
    suspend fun saveSubscriptions(metrics: List<String>) {
        return withContext(Dispatchers.IO) {
            api.saveSubscriptions(SaveMetricsRequest(metrics))
        }
    }
}