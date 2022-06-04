package com.polyactiveteam.polyactive.androidTests.screens

import com.polyactiveteam.polyactive.R

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId

class LoginScreen : BaseScreen() {

    private val appNameId = R.id.app_name
    private val fragmentLoginId = R.id.fragment_login
    private val googleSignInButtonId = R.id.google_sign_in_button
    private val polytechIconId = R.id.ic_polytech

    // Авторизация через Google работает неправильно, поэтому используем кнопку
    private val shadowButtonId = R.id.shadow_button

    init {
        onView(withId(fragmentLoginId)).check(matches(isDisplayed()))
    }

    fun goToFeedScreen(): FeedScreen {
        onView(withId(shadowButtonId)).check(matches(isDisplayed())).perform(click())
        return FeedScreen()
    }

    fun checkAfterTurnOver(): Boolean {
        return try {
            onView(withId(fragmentLoginId)).check(matches(isDisplayed()))
            onView(withId(appNameId)).check(matches(isDisplayed()))
            onView(withId(googleSignInButtonId)).check(matches(isDisplayed()))
            onView(withId(polytechIconId)).check(matches(isDisplayed()))
            onView(withId(shadowButtonId)).check(matches(isDisplayed()))
            true
        } catch (e: NoMatchingViewException) {
            false
        }
    }
}