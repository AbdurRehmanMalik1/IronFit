package com.workoutapp.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import com.workoutapp.R
import com.workoutapp.activities.LoginActivity
import com.workoutapp.activities.ReadableTermsActivity
import com.workoutapp.api.AuthRepository
import com.workoutapp.prefs.AppPrefs
import kotlinx.coroutines.launch


class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val context = requireContext()

        val tvName = view.findViewById<TextView>(R.id.tvName)
        val tvEmail = view.findViewById<TextView>(R.id.tvEmail)

        val btnLogout = view.findViewById<View>(R.id.btnLogout)

        val btnDarkMode = view.findViewById<LinearLayout>(R.id.btnDarkMode)

        val btnTermsAndConditions = view.findViewById<LinearLayout>(R.id.btnTermsAndConditions)

        val user = AppPrefs.getUser(requireContext())
        user?.let {
            tvName.text = it.name
            tvEmail.text = it.email
        }

        btnTermsAndConditions.setOnClickListener {
            context.startActivity(Intent(context, ReadableTermsActivity::class.java))
        }

        btnLogout.setOnClickListener {
            logout()
        }

        btnDarkMode.setOnClickListener {
            val currentMode = AppCompatDelegate.getDefaultNightMode()

            if (currentMode == AppCompatDelegate.MODE_NIGHT_YES) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                AppPrefs.setDarkMode(requireContext(), false)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                AppPrefs.setDarkMode(requireContext(), true)
            }
        }

    }

    private fun logout() {
        lifecycleScope.launch {
            try {
                AuthRepository().logout()
                clearLocalSession()
            } catch (e: Exception) {
                clearLocalSession()
            }
        }
    }

    private fun clearLocalSession() {
        val context = requireContext()

        // 2. Clear AppPrefs
        AppPrefs.setLoggedIn(context, false)
        AppPrefs.clearUser(context)
        AppPrefs.clearAll(context)

        // 3. Clear cookies (important for WebView / HTTP cookies)
        android.webkit.CookieManager.getInstance().apply {
            removeAllCookies(null)
            flush()
        }

        val intent = Intent(requireActivity(), LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}