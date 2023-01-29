package com.polyactiveteam.polyactive.androidTests.testSuites

import com.polyactiveteam.polyactive.androidTests.tests.feed.ExitFromFeedAfterSettingsTest
import com.polyactiveteam.polyactive.androidTests.tests.feed.ExitFromFeedAfterLoginTest
import com.polyactiveteam.polyactive.androidTests.tests.feed.ExitFromFeedAfterProfileTest
import com.polyactiveteam.polyactive.androidTests.tests.login.TurnLoginScreenTest

import org.junit.runner.RunWith
import org.junit.runners.Suite
import org.junit.runners.Suite.SuiteClasses

@RunWith(Suite::class)
@SuiteClasses(
    ExitFromFeedAfterProfileTest::class,
    ExitFromFeedAfterLoginTest::class,
    ExitFromFeedAfterSettingsTest::class,
    TurnLoginScreenTest::class
)
class AllTests {}