package com.workoutapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.workoutapp.api.AuthRepository
import com.workoutapp.models.api.response.User
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {

    private val repository = AuthRepository()

    val loginSuccess = MutableLiveData<Boolean>()
    val signupSuccess = MutableLiveData<Boolean>()

    val user = MutableLiveData<User>()

    val error = MutableLiveData<String>()


    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                val response = repository.login(email, password)

                if (response.isSuccessful) {
                    loginSuccess.value = true

                    // optional: fetch user after login
                    loadMe()

                } else {
                    error.value = response.message()
                    loginSuccess.value = false
                }

            } catch (e: Exception) {
                error.value = e.message
                loginSuccess.value = false
            }
        }
    }

    fun signup(name: String, email: String, password: String) {
        viewModelScope.launch {
            try {
                val response = repository.signup(name, email, password)

                if (response.isSuccessful) {
                    signupSuccess.value = true
                } else {
                    error.value = response.message()
                    signupSuccess.value = false
                }

            } catch (e: Exception) {
                error.value = e.message
                signupSuccess.value = false
            }
        }
    }

    fun loadMe() {
        viewModelScope.launch {
            try {
                val response = repository.me()

                if (response.isSuccessful) {
                    user.value = response.body()?.user
                } else {
                    error.value = response.message()
                }

            } catch (e: Exception) {
                error.value = e.message
            }
        }
    }
}