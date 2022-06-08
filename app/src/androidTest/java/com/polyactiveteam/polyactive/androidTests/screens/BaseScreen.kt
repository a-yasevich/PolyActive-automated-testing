package com.polyactiveteam.polyactive.androidTests.screens

import androidx.test.espresso.Espresso

open class BaseScreen {

    fun goBackWithBackButton() {
        Espresso.pressBackUnconditionally()
    }
}