package com.polyactiveteam.polyactive.androidTests.screens

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.polyactiveteam.polyactive.R

open class NavigableScreen {
    companion object {
        private const val FEED_BOTTOM_NAV_ITEM = R.id.action_feed
        private const val SETTINGS_BOTTOM_NAV_ITEM = R.id.action_settings
        private const val PROFILE_BOTTOM_NAV_ITEM = R.id.action_profile
    }

    fun goToSettingsScreen(): SettingsScreen {
        onView(withId(SETTINGS_BOTTOM_NAV_ITEM))
            .check(ViewAssertions.matches(isDisplayed()))
            .perform(click())
        return SettingsScreen()
    }

    fun goToProfileScreen(): ProfileScreen {
        onView(withId(PROFILE_BOTTOM_NAV_ITEM))
            .check(ViewAssertions.matches(isDisplayed()))
            .perform(click())
        return ProfileScreen()
    }

    fun goToFeedScreen(): FeedScreen {
        onView(withId(FEED_BOTTOM_NAV_ITEM))
            .check(ViewAssertions.matches(isDisplayed()))
            .perform(click())
        return FeedScreen()
    }

}