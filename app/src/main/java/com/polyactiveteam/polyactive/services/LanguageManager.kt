package com.polyactiveteam.polyactive.services

import android.content.Context
import android.content.res.Configuration
import android.preference.PreferenceManager
import java.util.Locale


class LanguageManager {
    companion object {
        private const val LANG_KEY = "Lang"

        //SDK 17+
        fun updateLanguage(language: String, context: Context): Context {
            val locale = Locale(language)
            Locale.setDefault(locale)
            val res = context.resources
            val config = Configuration(res.configuration)
            persist(language, context)
            config.setLocale(locale)
            return context.createConfigurationContext(config)
        }

        private fun persist(language: String, context: Context) {
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            val editor = preferences.edit()
            editor.putString(LANG_KEY, language)
            editor.apply()
        }

        fun updateWithPersisted(context: Context, defaultLanguage: String): Context {
            val language = getPersistedData(context, defaultLanguage)
            return updateLanguage(language, context)
        }

        //TODO avoid deprecated code
        private fun getPersistedData(context: Context, defaultLanguage: String): String {
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            return preferences.getString(LANG_KEY, defaultLanguage) ?: defaultLanguage
        }

    }
}
