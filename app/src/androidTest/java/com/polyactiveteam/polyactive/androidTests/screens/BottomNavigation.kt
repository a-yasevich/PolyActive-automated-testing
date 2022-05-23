package com.polyactiveteam.polyactive.androidTests.screens

import com.agoda.kakao.screen.Screen
import com.agoda.kakao.text.KButton
import com.polyactiveteam.polyactive.R

object BottomNavigation : Screen<BottomNavigation>() {
    val feedBottomNavItem = KButton { withId(R.id.action_feed) }
    val settingsBottomNavItem = KButton { withId(R.id.action_settings) }
    val profileBottomNavItem = KButton { withId(R.id.action_profile) }
}