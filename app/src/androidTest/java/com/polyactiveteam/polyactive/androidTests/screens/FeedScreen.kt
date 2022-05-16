package com.polyactiveteam.polyactive.androidTests.screens

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.polyactiveteam.polyactive.R

class FeedScreen {

    private val loginScreen: LoginScreen = LoginScreen()
    private val feedFragment = R.id.feed_fragment
    private val actionFeed = R.id.action_feed

    fun get() {
        loginScreen.logIn()
        onView(withId(actionFeed)).check(matches(isDisplayed())).perform(ViewActions.click())
        onView(withId(feedFragment)).check(matches(isDisplayed()))
    }
}
