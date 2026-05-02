package com.workoutapp.api

import com.workoutapp.models.api.request.LoginRequest
import com.workoutapp.models.api.request.SignupRequest
import com.workoutapp.models.api.response.AuthResponse
import com.workoutapp.models.api.response.MessageResponse
import com.workoutapp.models.api.response.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthApi {

    @POST("auth/signup")
    suspend fun signup(@Body request: SignupRequest): Response<AuthResponse>

    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Response<AuthResponse>

    @GET("auth/me")
    suspend fun me(): Response<UserResponse>

    @POST("auth/logout")
    suspend fun logout(): Response<MessageResponse>
}