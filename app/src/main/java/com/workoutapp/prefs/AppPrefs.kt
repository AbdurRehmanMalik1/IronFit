package com.workoutapp.prefs

import android.content.Context
import androidx.core.content.edit

object AppPrefs {

    private const val PREF_NAME = "app_prefs"

    private const val KEY_ONBOARDING = "is_onboarded"
    private const val KEY_LOGGED_IN = "is_logged_in"
    private const val KEY_FIRST_LAUNCH = "is_first_launch"

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
}
