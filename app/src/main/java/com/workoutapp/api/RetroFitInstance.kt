package com.workoutapp.api

import android.content.Context
import com.workoutapp.prefs.PersistentCookieJar
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance(context: Context) {

    private val baseURL = "http://192.168.1.17:5000/"

    private val logger = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val cookieJar = PersistentCookieJar(context)

    private val client = OkHttpClient.Builder()
        .addInterceptor(logger)
        .cookieJar(cookieJar)
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(baseURL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val authApi: AuthApi = retrofit.create(AuthApi::class.java)

    val metricsApi: MetricsApi = retrofit.create(MetricsApi::class.java)

}