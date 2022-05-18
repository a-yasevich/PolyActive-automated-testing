package com.polyactiveteam.polyactive.androidTests.screens

import com.agoda.kakao.screen.Screen
import com.agoda.kakao.text.KButton
import com.polyactiveteam.polyactive.R

object LoginScreen : Screen<LoginScreen>() {
    val loginButton = KButton { withId(R.id.shadow_button) }
}