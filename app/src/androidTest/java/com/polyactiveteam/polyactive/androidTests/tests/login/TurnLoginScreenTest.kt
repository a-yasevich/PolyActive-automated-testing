package com.polyactiveteam.polyactive.androidTests.tests.login

import android.content.pm.ActivityInfo
import com.polyactiveteam.polyactive.androidTests.screens.LoginScreen
import com.polyactiveteam.polyactive.androidTests.tests.BaseTest
import org.junit.Test

class TurnLoginScreenTest : BaseTest() {

    // Поварачиваем экран на экране авторизации -> проверяем, что все элементы отобразились
    @Test
    fun turnOverTest() {
        val loginScreen = LoginScreen()
        startingScreenRule.scenario.onActivity { activity ->
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }
        loginScreen.checkAfterTurnOver()
    }
}