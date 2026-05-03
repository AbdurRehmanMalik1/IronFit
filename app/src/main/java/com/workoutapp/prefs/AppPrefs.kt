package com.workoutapp.prefs

import android.content.Context
import androidx.core.content.edit
import com.google.gson.Gson
import com.workoutapp.activities.SplashScreenActivity
import com.workoutapp.models.api.response.User
import com.workoutapp.models.api.response.UserResponse

object AppPrefs {

    private const val PREF_NAME = "app_prefs"

    private const val KEY_ONBOARDING = "is_onboarded"
    private const val KEY_LOGGED_IN = "is_logged_in"
    private const val KEY_FIRST_LAUNCH = "is_first_launch"
    private const val KEY_TERMS_ACCEPTED = "is_terms_accepted"
    private const val KEY_USER_EMAIL = "user_email"
    private const val KEY_USER_PASSWORD = "user_password"

    private const val KEY_USER = "key_user"

    private val gson = Gson()


    private fun prefs(context: Context) =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun setOnboarded(context: Context, value: Boolean) {
        prefs(context).edit { putBoolean(KEY_ONBOARDING, value) }
    }

    fun isOnboarded(context: Context): Boolean {
        return prefs(context).getBoolean(KEY_ONBOARDING, false)
    }

    fun setLoggedIn(context: Context, value: Boolean) {
        prefs(context).edit { putBoolean(KEY_LOGGED_IN, value) }
    }

    fun isLoggedIn(context: Context): Boolean {
        return prefs(context).getBoolean(KEY_LOGGED_IN, false)
    }

    fun setFirstLaunch(context: Context, value: Boolean) {
        prefs(context).edit { putBoolean(KEY_FIRST_LAUNCH, value) }
    }

    fun isFirstLaunch(context: Context): Boolean {
        return prefs(context).getBoolean(KEY_FIRST_LAUNCH, true)
    }

    fun clearAll(context: Context) {
        prefs(context).edit { clear() }
    }

    fun setTermsAccepted(context: Context, value: Boolean) {
        prefs(context).edit { putBoolean(KEY_TERMS_ACCEPTED, value) }
    }

    fun isTermsAccepted(context: Context): Boolean {
        return prefs(context).getBoolean(KEY_TERMS_ACCEPTED, false)
    }

    fun saveCredentials(context: Context, email: String, password: String) {
        prefs(context).edit {
            putString(KEY_USER_EMAIL, email)
            putString(KEY_USER_PASSWORD, password)
        }
    }

    fun getSavedEmail(context: Context): String {
        return prefs(context).getString(KEY_USER_EMAIL, "") ?: ""
    }

    fun isValidCredentials(context: Context, email: String, password: String): Boolean {
        val savedEmail = prefs(context).getString(KEY_USER_EMAIL, "") ?: ""
        val savedPassword = prefs(context).getString(KEY_USER_PASSWORD, "") ?: ""
        return savedEmail.equals(email, ignoreCase = true) && savedPassword == password
    }

    fun saveUser(context: Context, user: User?) {
        prefs(context).edit {
            if (user != null) {
                putString(KEY_USER, gson.toJson(user))
            } else {
                remove(KEY_USER)
            }
        }
    }

    fun getUser(context: Context): User? {
        val json = prefs(context).getString(KEY_USER, null)
        return json?.let { gson.fromJson(it, User::class.java) }
    }

    fun clearUser(context: Context) {
        prefs(context).edit { remove(KEY_USER) }
    }
}
