package com.workoutapp.viewmodels

data class MetricSelectUI(
    val type: String,
    val title: String,
    val unit: String,
    val iconRes: Int,
    val color: String,
    var isSelected: Boolean = false
)