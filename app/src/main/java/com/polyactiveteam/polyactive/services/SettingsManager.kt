package com.polyactiveteam.polyactive.services

import android.content.Context
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatDelegate
import java.util.Locale


class SettingsManager {
    companion object {
        private const val LANG_KEY = "Lang"
        private const val THEME_KEY = "Theme"
        private const val PREFS_FILE_NAME = "Settings"

        fun updateLanguage(language: String, context: Context): Context {
            val locale = Locale(language)
            Locale.setDefault(locale)
            val res = context.resources
            val config = Configuration(res.configuration)
            persist(language, context)
            config.setLocale(locale)
            return context.createConfigurationContext(config)
        }

        fun updateTheme(nightModeEnabled: Boolean, context: Context) {
            persist(nightModeEnabled, context)
            val mode = mode(nightModeEnabled)
            AppCompatDelegate.setDefaultNightMode(mode)
        }

        fun updateWithPersisted(
            context: Context,
            defaultLanguage: String,
            defaultNightModeEnabled: Boolean
        ): Context {
            val language = getPersistedData(context, defaultLanguage)
            val persistedMode = getPersistedData(context, defaultNightModeEnabled)
            AppCompatDelegate.setDefaultNightMode(mode(persistedMode))
            return updateLanguage(language, context)
        }

        private fun persist(language: String, context: Context) {
            val preferences = context.getSharedPreferences(PREFS_FILE_NAME, Context.MODE_PRIVATE)
            with(preferences.edit()) {
                putString(LANG_KEY, language)
                apply()
            }
        }

        private fun persist(nightModeEnabled: Boolean, context: Context) {
            val preferences = context.getSharedPreferences(PREFS_FILE_NAME, Context.MODE_PRIVATE)
            with(preferences.edit()) {
                putBoolean(THEME_KEY, nightModeEnabled)
                apply()
            }
        }


        private fun getPersistedData(context: Context, defaultLanguage: String): String {
            val preferences = context.getSharedPreferences(PREFS_FILE_NAME, Context.MODE_PRIVATE)
            return preferences.getString(LANG_KEY, defaultLanguage) ?: defaultLanguage
        }

        private fun getPersistedData(context: Context, defaultNightModeEnabled: Boolean): Boolean {
            val preferences = context.getSharedPreferences(PREFS_FILE_NAME, Context.MODE_PRIVATE)
            return preferences.getBoolean(THEME_KEY, defaultNightModeEnabled)
        }

        private fun mode(nightMode: Boolean) =
            if (nightMode) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
    }
}
