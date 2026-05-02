package com.workoutapp.api

import android.content.Context

object ApiProvider {
    private var instance: RetrofitInstance? = null

    fun getInstance(context: Context): RetrofitInstance {
        if (instance == null) {
            instance = RetrofitInstance(context.applicationContext)
        }
        return instance!!
    }
}