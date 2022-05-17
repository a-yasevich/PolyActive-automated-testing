package com.polyactiveteam.polyactive.androidTests.tests

import com.agoda.kakao.screen.Screen
import com.agoda.kakao.text.KTextView
import com.polyactiveteam.polyactive.R

object NewsScreen : Screen<NewsScreen>() {
    val newsDescription = KTextView { withId(R.id.news_viewer__description) }
}