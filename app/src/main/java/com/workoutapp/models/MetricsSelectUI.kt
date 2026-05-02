package com.workoutapp.models

data class MetricsSelectUI(
    val type: String,
    val title: String,
    val unit: String,
    val iconRes: Int,
    val color: String,
    var isSelected: Boolean = false
)