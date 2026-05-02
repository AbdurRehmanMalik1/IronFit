package com.workoutapp.prefs

import android.content.Context
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import org.json.JSONArray

class PersistentCookieJar(context: Context) : CookieJar {

    private val prefs = context.getSharedPreferences("cookies", Context.MODE_PRIVATE)

    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        val jsonArray = JSONArray()

        cookies.forEach {
            jsonArray.put(it.toString())
        }

        prefs.edit().putString(url.host, jsonArray.toString()).apply()
    }

    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        val cookiesJson = prefs.getString(url.host, null) ?: return emptyList()

        val jsonArray = JSONArray(cookiesJson)
        val cookies = mutableListOf<Cookie>()

        for (i in 0 until jsonArray.length()) {
            val cookieString = jsonArray.getString(i)
            Cookie.parse(url, cookieString)?.let {
                cookies.add(it)
            }
        }

        return cookies
    }

    fun clear() {
        prefs.edit().clear().apply()
    }
}