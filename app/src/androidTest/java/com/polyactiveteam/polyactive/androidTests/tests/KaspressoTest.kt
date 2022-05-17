package com.polyactiveteam.polyactive.androidTests.tests

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.polyactiveteam.polyactive.MainActivity
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class KaspressoTest :
    KaspressoLifecycleTest<MainActivity>(MainActivity::class.java) {

    override fun onSetup() {}

    @Test
    fun clickButton() {
        LoginScreen {
            before {
                // Prepare test case
            }.after {
                // Do something on end of the test
            }.run {
                step("Click login button") { firstButton.click() }
                step("Check if all buttons are visible") {
                    //CheckAllButtonsAreVisible()
                }
            }
        }
        run {
            FeedScreen {
                step("Press on first news") {
                    feedList {
                        scrollTo(0)
                        childAt<FeedScreen.Item>(0) {
                            isVisible()
                            click()
                        }
                    }
                }
            }
        }
    }
}

//class CheckAllButtonsAreVisible : Scenario() {
//    override val steps: TestContext<Unit>.() -> Unit
//        get() = {
//            ManyStepsScreen {
//                firstButton.isVisible()
//                secondButton.isVisible()
//                thirdButton.isVisible()
//            }
//        }
//}