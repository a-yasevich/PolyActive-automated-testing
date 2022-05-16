package com.polyactiveteam.polyactive.fragments.screens

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.polyactiveteam.polyactive.R

class LoginScreen {

    private val googleSignInButton = R.id.google_sign_in_button
    private val fragmentProfile = R.id.fragment_profile

    fun loginAlreadyInGoogleAccount() {
        onView(withId(googleSignInButton)).check(matches(isDisplayed())).perform(click())
        onView(withId(fragmentProfile)).check(matches(isDisplayed()))
    }
}