package com.workoutapp.models

data class MetricDefinition(
    val type: String,
    val name: String,
    val unit: String,
    val icon: String,
    val color: String
)

data class MetricUI(
    val type: String,
    val title: String,
    val value: String,
    val unit: String,
    val iconResId: Int,
    val colorHex: String
)