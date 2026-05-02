package com.workoutapp.logger
import android.content.Context
import android.util.Log
import android.widget.Toast
import retrofit2.Response

object ApiLogger {

    private const val TAG = "API_LOG"

    // 🔵 SUCCESS LOG
    fun <T> logSuccess(context: Context, endpoint: String, response: Response<T>) {

        val body = response.body()

        val message = """
            ✅ API SUCCESS: $endpoint
            Code: ${response.code()}
            Body: $body
        """.trimIndent()

        Log.d(TAG, message)

        Toast.makeText(
            context,
            "Success: ${response.code()}",
            Toast.LENGTH_SHORT
        ).show()
    }

    // 🔴 ERROR LOG (FULL BODY)
    fun logError(context: Context, endpoint: String, response: Response<*>) {

        val errorBody = try {
            response.errorBody()?.string()
        } catch (e: Exception) {
            "Could not read error body"
        }

        val message = """
            ❌ API ERROR: $endpoint
            Code: ${response.code()}
            Message: $errorBody
        """.trimIndent()

        Log.e(TAG, message)

        Toast.makeText(
            context,
            "Error ${response.code()}: ${errorBody ?: "Unknown"}",
            Toast.LENGTH_LONG
        ).show()
    }

    // 🔥 EXCEPTION LOG (NETWORK FAILURES)
    fun logException(context: Context, endpoint: String, e: Exception) {

        val message = """
            💥 EXCEPTION: $endpoint
            Error: ${e.message}
        """.trimIndent()

        Log.e(TAG, message, e)

        Toast.makeText(
            context,
            "Exception: ${e.message}",
            Toast.LENGTH_LONG
        ).show()
    }
}