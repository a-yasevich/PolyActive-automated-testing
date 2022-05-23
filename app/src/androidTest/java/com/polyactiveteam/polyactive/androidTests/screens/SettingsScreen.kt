package com.polyactiveteam.polyactive.androidTests.screens

import com.agoda.kakao.image.KImageView
import com.agoda.kakao.screen.Screen
import com.agoda.kakao.text.KTextView
import com.polyactiveteam.polyactive.R

object SettingsScreen : Screen<SettingsScreen>(), NavigableScreen {
    val russianButton = KImageView { withId(R.id.language_iv1) }
    val englishButton = KImageView { withId(R.id.language_iv2) }
    val languageTextFiled = KTextView { withId(R.id.language_tv) }
}