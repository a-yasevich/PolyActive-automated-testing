package com.polyactiveteam.polyactive.androidTests.screens

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.polyactiveteam.polyactive.R
import com.polyactiveteam.polyactive.model.News

class FeedScreen {
    private val feedFragment = R.id.feed_fragment
    private val feedBottomNavItemId = R.id.action_feed
    private val settingsBottomNavItemId = R.id.action_settings

    fun goToSettingsScreen(): SettingsScreen {
        onView(withId(settingsBottomNavItemId))
            .check(matches(isDisplayed()))
            .perform(click())
        return SettingsScreen()
    }

    fun checkNews(news: News){
    }

    fun checkOpenNews(news: News){

    }
}
