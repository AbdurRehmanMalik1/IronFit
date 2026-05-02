package com.workoutapp.api

import android.content.Context
import com.workoutapp.models.api.request.LoginRequest
import com.workoutapp.models.api.request.SignupRequest

class AuthRepository(context: Context) {
    private val api = ApiProvider.getInstance(context).api
    suspend fun signup(name: String, email: String, password: String) =
        api.signup(
            SignupRequest(name, email, password)
        )

    suspend fun login(email: String, password: String) =
        api.login(
            LoginRequest(email, password)
        )

    suspend fun me() = api.me()
}