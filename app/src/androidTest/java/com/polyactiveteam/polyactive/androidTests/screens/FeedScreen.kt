package com.polyactiveteam.polyactive.androidTests.screens

import com.polyactiveteam.polyactive.R

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId

class FeedScreen : BaseScreen() {

    private val feedFragmentId = R.id.feed_fragment
    private val bottomNavigationId = R.id.bottom_navigation
    private val profileBottomNavigationItemId = R.id.action_profile
    private val settingsBottomNavigationItemId = R.id.action_settings

    init {
        onView(withId(feedFragmentId)).check(matches(isDisplayed()))
        onView(withId(bottomNavigationId)).check(matches(isDisplayed()))
    }

    fun goToProfileScreen(): ProfileScreen {
        onView(withId(profileBottomNavigationItemId))
            .check(matches(isDisplayed()))
            .perform(click())
        return ProfileScreen()
    }

    fun goToSettingsScreen(): SettingsScreen {
        onView(withId(settingsBottomNavigationItemId))
            .check(matches(isDisplayed()))
            .perform(click())
        return SettingsScreen()
    }
}