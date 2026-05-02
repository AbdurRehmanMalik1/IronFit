package com.workoutapp

import android.app.Application
import com.workoutapp.api.ApiProvider

class IronFitApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        ApiProvider.init(this)
    }
}
