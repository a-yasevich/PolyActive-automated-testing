package com.polyactiveteam.polyactive.androidTests.screens

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.polyactiveteam.polyactive.R
import com.polyactiveteam.polyactive.model.News

class NewsViewerScreen {
    private val newsDescription = R.id.news_viewer__description

    fun checkEquality(news: News) {
        onView(withId(newsDescription))
            .check(matches(isDisplayed()))
            .check(matches(withText(news.newsDescription)))
    }
}