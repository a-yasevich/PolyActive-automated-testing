package com.polyactiveteam.polyactive.androidTests.screens

import com.agoda.kakao.screen.Screen
import com.agoda.kakao.text.KTextView
import com.polyactiveteam.polyactive.R

object NewsScreen : Screen<NewsScreen>() {
    val description = KTextView { withId(R.id.news_viewer__description) }
    val header = KTextView { withId(R.id.news_viewer__header) }
}