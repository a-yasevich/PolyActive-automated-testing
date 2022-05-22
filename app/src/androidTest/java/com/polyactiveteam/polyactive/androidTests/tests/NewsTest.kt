package com.polyactiveteam.polyactive.androidTests.tests

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.polyactiveteam.polyactive.MainActivity
import com.polyactiveteam.polyactive.androidTests.screens.FeedScreen
import com.polyactiveteam.polyactive.androidTests.screens.LoginScreen
import com.polyactiveteam.polyactive.androidTests.screens.NewsScreen
import com.polyactiveteam.polyactive.model.News
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.math.min

@RunWith(AndroidJUnit4::class)
class NewsTest :
    BaseTest<MainActivity>(MainActivity::class.java) {

    override fun onSetup() {}

    @Test
    fun checkNews() {
        run {
            step("Logging in") {
                LoginScreen {
                    loginButton {
                        isVisible()
                        click()
                    }
                }
            }
            step("View news one by one") {
                for (i in 0 until min(10, FeedScreen.feedList.getSize())) {
                    var news: News? = null
                    step("Clicking on the i-th news") {
                        FeedScreen {
                            feedList {
                                scrollTo(i)
                                isVisible()
                                childAt<FeedScreen.NewsItem>(i) {
                                    isVisible()
                                    news = newsObj()
                                    click()
                                }
                            }
                        }
                    }
                    step("Check that news is the same as the one we clicked on") {
                        NewsScreen {
                            header {
                                isVisible()
                                hasText(news!!.header)
                            }
                            description {
                                isVisible()
                                hasText(news!!.newsDescription)
                            }
                            pressBack()
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