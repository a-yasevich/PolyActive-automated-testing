package com.polyactiveteam.polyactive.androidTests.tests.login

import com.polyactiveteam.polyactive.R
import com.polyactiveteam.polyactive.androidTests.screens.LoginScreen
import com.polyactiveteam.polyactive.androidTests.tests.BaseTest
import com.polyactiveteam.polyactive.model.News
import org.junit.Test

class NewsTest : BaseTest() {

    private val testNews = News(
        R.drawable.ic_news_plug,
        "Новый RecycleView",
        "Команда PolyActive добавила RecycleView к своему проекту",
        1648329900,
        0
    )

    @Test
    fun viewTestNews() {
        LoginScreen().logIn()
            .checkEqualityWithNewsOnPosition(testNews, 0)
            .pressNewsAtPosition(0)
            .checkEquality(testNews)
    }
}