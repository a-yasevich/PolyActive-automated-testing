package com.polyactiveteam.polyactive.androidTests.screens

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers.*
import com.polyactiveteam.polyactive.R
import com.polyactiveteam.polyactive.model.News
import org.hamcrest.Matcher


class FeedScreen {
    private val feedFragment = R.id.feed_fragment
    private val feedBottomNavItemId = R.id.action_feed
    private val settingsBottomNavItemId = R.id.action_settings
    private val newsList = R.id.news_list

    fun goToSettingsScreen(): SettingsScreen {
        onView(withId(settingsBottomNavItemId))
            .check(matches(isDisplayed()))
            .perform()
        return SettingsScreen()
    }

    fun checkEqualityWithNewsOnPosition(news: News, position: Int): FeedScreen {
        onView(withId(newsList))
            .check(matches(matchesAtPosition(position, hasDescendant(withText(news.newsDescription)))))
        return this
    }

    fun pressNewsAtPosition(position: Int): NewsViewerScreen {
        onView(withId(newsList))
            .perform(actionAtPosition(position, click()))
        return NewsViewerScreen()
    }

    private fun matchesAtPosition(position: Int, itemMatcher: Matcher<View>): Matcher<View?> {
        return object : BoundedMatcher<View?, RecyclerView>(RecyclerView::class.java) {
            override fun matchesSafely(view: RecyclerView): Boolean {
                val viewHolder = view.findViewHolderForAdapterPosition(position)
                    ?: return false
                return itemMatcher.matches(viewHolder.itemView)
            }

            override fun describeTo(description: org.hamcrest.Description?) {
                description?.appendText("has item at position $position: ")
                itemMatcher.describeTo(description)
            }
        }
    }

    private fun actionAtPosition(position: Int, itemAction: ViewAction): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return isDisplayed()
            }

            override fun getDescription(): String {
                return "Performing $itemAction at position $position"
            }

            override fun perform(uiController: UiController?, view: View?) {
                val viewHolder = (view as RecyclerView).findViewHolderForAdapterPosition(position)
                    ?: return
                itemAction.perform(uiController, viewHolder.itemView)
                Log.d("MYLOG", "Pressed")
            }
        }
    }
}
