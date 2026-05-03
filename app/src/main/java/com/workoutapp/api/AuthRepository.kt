package com.workoutapp.api

import com.workoutapp.models.api.request.LoginRequest
import com.workoutapp.models.api.request.SignupRequest

class AuthRepository {
    private val api = ApiProvider.authApi()
    suspend fun signup(name: String, email: String, password: String) =
        api.signup(
            SignupRequest(name, email, password)
        )

    suspend fun login(email: String, password: String) =
        api.login(
            LoginRequest(email, password)
        )

    suspend fun me() = api.me()

    suspend fun logout() = api.logout()
}