package com.polyactiveteam.polyactive.fragments.tests

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.polyactiveteam.polyactive.MainActivity
import com.polyactiveteam.polyactive.R
import com.polyactiveteam.polyactive.fragments.screens.LoginScreen
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class LoginFragmentTest {

    @get:Rule
    val startingScreenRule = ActivityScenarioRule(MainActivity::class.java)

    private val loginScreen: LoginScreen = LoginScreen()
    private val fragmentProfile = R.id.fragment_profile
    private val avatarImage = R.id.avatar_image
    private val userName = R.id.user_name

    @Test
    fun alreadyLoginTest() {
        loginScreen.loginAlreadyInGoogleAccount()
        onView(withId(fragmentProfile)).check(matches(isDisplayed()))
        onView(withId(avatarImage)).check(matches(isDisplayed()))
        onView(withId(userName)).check(matches(isDisplayed()))
    }
}