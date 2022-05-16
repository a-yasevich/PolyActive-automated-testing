package com.polyactiveteam.polyactive.androidTests.screens

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.polyactiveteam.polyactive.R

class LoginScreen {

    private val googleSignInButton = R.id.shadow_button
    private val fragmentFeed = R.id.feed_fragment

    fun logIn(): FeedScreen {
        onView(withId(googleSignInButton)).check(matches(isDisplayed())).perform(click())
        onView(withId(fragmentFeed)).check(matches(isDisplayed()))
        return FeedScreen()
    }
}