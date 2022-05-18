package com.polyactiveteam.polyactive.androidTests.tests.espresso

import com.polyactiveteam.polyactive.androidTests.screens.pageobjs.LoginScreen
import org.junit.Test

class LoginTest : BaseTest() {

    @Test
    fun justLoginTest() {
        val loginScreen = LoginScreen()
        loginScreen.logIn()
    }
}