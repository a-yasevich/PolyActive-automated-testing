package com.polyactiveteam.polyactive.androidTests.tests.feed

import com.polyactiveteam.polyactive.androidTests.screens.LoginScreen
import com.polyactiveteam.polyactive.androidTests.tests.BaseTest

import androidx.lifecycle.Lifecycle
import org.junit.Assert.assertTrue
import org.junit.Test

class ExitFromFeedAfterLoginTest : BaseTest(), ExitFromFeedInterface {

    // Авторизуемся -> Выходим из приложения по нажатию кнопки назад
    @Test
    override fun exitFromFeedTest() {
        LoginScreen()
            .goToFeedScreen()
            .goBackWithBackButton()
        assertTrue(startingScreenRule.scenario.state.isAtLeast(Lifecycle.State.DESTROYED))
    }
}