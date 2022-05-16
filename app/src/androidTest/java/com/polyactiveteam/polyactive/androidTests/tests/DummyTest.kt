package com.polyactiveteam.polyactive.androidTests.tests
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
//import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.polyactiveteam.polyactive.MainActivity
import com.polyactiveteam.polyactive.R
import com.polyactiveteam.polyactive.androidTests.screens.FeedScreen
import com.polyactiveteam.polyactive.androidTests.screens.LoginScreen
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)

class DummyTest {
    @get:Rule
    val startingScreenRule = ActivityScenarioRule(MainActivity::class.java)

    private val loginScreen = LoginScreen()
    private val newsList = R.id.news_list
    private val fragmentNewsViewer = R.id.fragment_news_viewer

    @Test
    fun newsOpen() {
        val feedScreen = loginScreen.logIn()
        onView(withId(newsList))
            .check(matches(isDisplayed()))
            //.perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(fragmentNewsViewer)).check(matches(isDisplayed()))
    }

}