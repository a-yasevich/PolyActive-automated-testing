package com.polyactiveteam.polyactive.androidTests.tests.login

import android.content.pm.ActivityInfo
import com.polyactiveteam.polyactive.androidTests.screens.LoginScreen
import com.polyactiveteam.polyactive.androidTests.tests.BaseTest
import org.junit.Test

class SettingsTest : BaseTest() {

    @Test
    fun switchToEnglishTest() {
        LoginScreen().logIn()
            .goToSettingsScreen()
            .switchToEnglish()
            .checkFields("en")
    }
}