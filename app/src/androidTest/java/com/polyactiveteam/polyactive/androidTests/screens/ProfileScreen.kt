package com.polyactiveteam.polyactive.androidTests.screens

import com.agoda.kakao.screen.Screen
import com.agoda.kakao.text.KButton
import com.polyactiveteam.polyactive.R

object ProfileScreen : Screen<ProfileScreen>(), NavigableScreen {
    val exitButton = KButton { withId(R.id.button_exit) }
    val profButton = KButton { withId(R.id.prof_button) }
    val brigadesButton = KButton { withId(R.id.brigades_button) }
    val adaptersButton = KButton { withId(R.id.adapters_button) }
}