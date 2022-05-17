package com.polyactiveteam.polyactive.androidTests.screens

import android.content.res.Configuration
import android.content.res.Resources
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry
import com.polyactiveteam.polyactive.R
import java.util.*

class SettingsScreen {
    companion object {
        private const val englishLanguageButton = R.id.language_iv2
        private const val languageTextField = R.id.language_tv
        private const val themeTextField = R.id.theme_tv
        private const val profileBottomNavItemId = R.id.action_profile
    }

    fun goToProfileScreen(): ProfileScreen {
        onView(withId(profileBottomNavItemId))
            .check(matches(isDisplayed()))
            .perform(click())
        return ProfileScreen()
    }

    fun switchToEnglish(): SettingsScreen {
        onView(withId(englishLanguageButton))
            .check(matches(isDisplayed()))
            .perform(click())
        return this
    }

    fun checkFields(localeName: String) {
        onView(withId(languageTextField))
            .check(matches(isDisplayed()))
            .check(matches(withText(getResourceString(R.string.language_field, localeName))))
        onView(withId(themeTextField))
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