package com.polyactiveteam.polyactive.androidTests.screens

import com.polyactiveteam.polyactive.R

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId

class ProfileScreen {

    private val profileFragmentId = R.id.fragment_profile
    private val bottomNavigationId = R.id.bottom_navigation
    private val feedBottomNavigationItemId = R.id.action_feed

    init {
        onView(withId(profileFragmentId)).check(matches(isDisplayed()))
        onView(withId(bottomNavigationId)).check(matches(isDisplayed()))
    }

    fun goToFeedScreen(): FeedScreen {
        onView(withId(feedBottomNavigationItemId))
            .check(matches(isDisplayed()))
            .perform(ViewActions.click())
        return FeedScreen()
    }
}