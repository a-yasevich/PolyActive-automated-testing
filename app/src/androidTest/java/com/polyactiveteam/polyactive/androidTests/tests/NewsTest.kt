package com.polyactiveteam.polyactive.androidTests.tests.login

import android.content.pm.ActivityInfo
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
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
    fun checkOpenedNewsIsEqual() {
        val feedScreen = LoginScreen()
            .logIn()
            .checkNews(testNews)
    }
}