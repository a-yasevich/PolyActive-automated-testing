package com.polyactiveteam.polyactive.androidTests.tests.espresso

import com.polyactiveteam.polyactive.androidTests.screens.pageobjs.LoginScreen
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