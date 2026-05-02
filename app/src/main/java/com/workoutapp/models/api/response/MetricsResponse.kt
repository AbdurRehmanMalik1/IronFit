package com.workoutapp.models.api.response

import com.workoutapp.models.MetricUI
import com.workoutapp.utils.getDrawableByName

fun mapToUI(list: List<MetricResponse>): List<MetricUI> {
    return list.map {
        MetricUI(
            type = it.type,
            title = it.name,
            value = it.value.toString(),
            unit = it.unit,
            iconResId = getDrawableByName(it.icon),
            colorHex = it.color
        )
    }
}

data class MetricResponse(
    val type: String,
    val name: String,
    val value: Double,
    val unit: String,
    val icon: String,
    val color: String
)

data class SubscriptionResponse(
    val metricType: String
)