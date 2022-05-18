package com.polyactiveteam.polyactive.androidTests.tests.espresso

import com.polyactiveteam.polyactive.MainActivity

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
open class BaseTest {

    @get:Rule
    val startingScreenRule = ActivityScenarioRule(MainActivity::class.java)
}