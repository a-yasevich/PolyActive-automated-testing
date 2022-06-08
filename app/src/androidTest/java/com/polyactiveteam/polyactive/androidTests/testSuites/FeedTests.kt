package com.polyactiveteam.polyactive.androidTests.testSuites

import com.polyactiveteam.polyactive.androidTests.tests.feed.ExitFromFeedAfterSettingsTest
import com.polyactiveteam.polyactive.androidTests.tests.feed.ExitFromFeedAfterLoginTest
import com.polyactiveteam.polyactive.androidTests.tests.feed.ExitFromFeedAfterProfileTest

import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
    ExitFromFeedAfterProfileTest::class,
    ExitFromFeedAfterLoginTest::class,
    ExitFromFeedAfterSettingsTest::class
)
class FeedTests {
}