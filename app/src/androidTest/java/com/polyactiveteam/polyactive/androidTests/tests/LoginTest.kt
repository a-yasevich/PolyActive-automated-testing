package com.polyactiveteam.polyactive.androidTests.tests.login

import android.content.pm.ActivityInfo
import com.polyactiveteam.polyactive.androidTests.screens.LoginScreen
import com.polyactiveteam.polyactive.androidTests.tests.BaseTest
import org.junit.Test

class LoginTest : BaseTest() {

    @Test
    fun justLoginTest() {
        val loginScreen = LoginScreen()
        loginScreen.logIn()
    }
}