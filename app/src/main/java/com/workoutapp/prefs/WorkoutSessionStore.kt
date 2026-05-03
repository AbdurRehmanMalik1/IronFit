package com.workoutapp.prefs

import androidx.core.content.edit

object WorkoutSessionStore {

    private const val PREF = "workout_session"

    private const val KEY_ID = "id"
    private const val KEY_START = "start"
    private const val KEY_ELAPSED = "elapsed"
    private const val KEY_RUNNING = "running"

    fun save(context: android.content.Context, id: String, start: Long, elapsed: Int, running: Boolean) {
        val prefs = context.getSharedPreferences(PREF, android.content.Context.MODE_PRIVATE)
        prefs.edit {
            putString(KEY_ID, id)
                .putLong(KEY_START, start)
                .putInt(KEY_ELAPSED, elapsed)
                .putBoolean(KEY_RUNNING, running)
        }
    }

    fun get(context: android.content.Context): WorkoutSession? {
        val prefs = context.getSharedPreferences(PREF, android.content.Context.MODE_PRIVATE)

        val id = prefs.getString(KEY_ID, null) ?: return null

        return WorkoutSession(
            id,
            prefs.getLong(KEY_START, 0),
            prefs.getInt(KEY_ELAPSED, 0),
            prefs.getBoolean(KEY_RUNNING, false)
        )
    }

    fun clear(context: android.content.Context) {
        context.getSharedPreferences(PREF, android.content.Context.MODE_PRIVATE)
            .edit {
                clear()
            }
    }
}

data class WorkoutSession(
    val id: String,
    val startTime: Long,
    val elapsed: Int,
    val running: Boolean
)