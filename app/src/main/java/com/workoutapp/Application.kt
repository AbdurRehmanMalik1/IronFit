package com.workoutapp

import android.app.Application
import com.workoutapp.api.ApiProvider

class WorkoutApp : Application() {

    override fun onCreate() {
        super.onCreate()

        ApiProvider.init(this)
    }
}
