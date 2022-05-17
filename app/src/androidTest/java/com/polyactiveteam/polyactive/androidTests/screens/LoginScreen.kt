package com.polyactiveteam.polyactive.androidTests.screens

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.polyactiveteam.polyactive.R

class LoginScreen {
    companion object{
        private const val SIGN_IN_BUTTON = R.id.shadow_button
        private const val FRAGMENT_FEED = R.id.feed_fragment
    }

    fun logIn(): FeedScreen {
        onView(withId(SIGN_IN_BUTTON)).check(matches(isDisplayed())).perform(click())
        onView(withId(FRAGMENT_FEED)).check(matches(isDisplayed()))
        return FeedScreen()
    }
}