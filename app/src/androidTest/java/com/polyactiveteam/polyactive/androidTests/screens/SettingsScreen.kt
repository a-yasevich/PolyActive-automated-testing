package com.polyactiveteam.polyactive.androidTests.screens

import android.content.res.Configuration
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry
import com.polyactiveteam.polyactive.R
import java.util.*

class SettingsScreen : NavigableScreen() {
    companion object {
        private const val ENGLISH_LAN_BUTTON = R.id.language_iv2
        private const val LANGUAGE_TEXT_FIELD = R.id.language_tv
        private const val THEME_TEXT_FIELD = R.id.theme_tv
    }

    fun switchToEnglish(): SettingsScreen {
        onView(withId(ENGLISH_LAN_BUTTON))
            .check(matches(isDisplayed()))
            .perform(click())
        return this
    }

    fun checkFields(localeName: String) {
        onView(withId(LANGUAGE_TEXT_FIELD))
            .check(matches(isDisplayed()))
            .check(matches(withText(getResourceString(R.string.language_field, localeName))))
        onView(withId(THEME_TEXT_FIELD))
            .check(matches(isDisplayed()))
            .check(matches(withText(getResourceString(R.string.theme_field, localeName))))
    }

    private fun getResourceString(id: Int, localeName: String): String {
        val config = Configuration()
        val locale = Locale(localeName)
        config.locale = locale
        val resources = InstrumentationRegistry.getInstrumentation().targetContext.resources
        resources.updateConfiguration(config, resources.displayMetrics)
        return resources.getString(id)
    }

}