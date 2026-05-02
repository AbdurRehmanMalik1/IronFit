package com.workoutapp.models.api.response

data class AuthResponse(
    val message: String,
    val user: User
)

data class User(
    val id: String,
    val email: String,
    val name: String
)

data class UserResponse(
    val user: User
)

data class MessageResponse(
    val message: String
)