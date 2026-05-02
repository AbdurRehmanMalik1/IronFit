package com.workoutapp.api

import android.content.Context
import com.workoutapp.prefs.PersistentCookieJar
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance(context: Context) {

    private val baseURL = "your url"

    private val logger = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val cookieJar = PersistentCookieJar(context)

    private val client = OkHttpClient.Builder()
        .addInterceptor(logger)
        .cookieJar(cookieJar)
        .build()

    val api: AuthApi = Retrofit.Builder()
        .baseUrl(baseURL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(AuthApi::class.java)
}