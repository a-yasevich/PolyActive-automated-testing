package com.polyactiveteam.polyactive.androidTests.screens

import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import com.polyactiveteam.polyactive.R

open class NavigableScreen {
    private val feedBottomNavItemId = R.id.action_feed
    private val settingsBottomNavItemId = R.id.action_settings
    private val profileBottomNavItemId = R.id.action_profile

    fun goToSettingsScreen(): SettingsScreen {
        onView(ViewMatchers.withId(settingsBottomNavItemId))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
            .perform()
        return SettingsScreen()
    }

    fun goToProfileScreen(): ProfileScreen {
        onView(ViewMatchers.withId(profileBottomNavItemId))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
            .perform(ViewActions.click())
        return ProfileScreen()
    }

    fun goToFeedScreen(): FeedScreen {
        onView(ViewMatchers.withId(feedBottomNavItemId))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
            .perform(ViewActions.click())
        return FeedScreen()
    }
}