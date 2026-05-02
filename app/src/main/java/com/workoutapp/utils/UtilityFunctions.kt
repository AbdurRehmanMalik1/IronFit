package com.workoutapp.utils

import com.workoutapp.R

fun getDrawableByName(name: String): Int {
    return when (name) {
        "ic_water_drop" -> R.drawable.ic_water_drop
        "ic_fire" -> R.drawable.ic_fire
        "ic_run" ->R.drawable.ic_run
        "ic_heart_rate" -> R.drawable.ic_heart_rate
        "ic_time_workout" -> R.drawable.ic_time_workout
        else -> R.drawable.ic_app_logo
    }
}

