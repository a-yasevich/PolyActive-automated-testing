package com.polyactiveteam.polyactive.androidTests.tests

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.polyactiveteam.polyactive.MainActivity
import com.polyactiveteam.polyactive.R
import com.polyactiveteam.polyactive.model.News
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class KaspressoTest :
    KaspressoLifecycleTest<MainActivity>(MainActivity::class.java) {
    private val testNews = News(
        R.drawable.ic_news_plug,
        "Новый RecycleView",
        "Команда PolyActive добавила RecycleView к своему проекту",
        1648329900,
        0
    )

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
            step("Clicking the first news") {
                FeedScreen {
                    feedList {
                        scrollTo(0)
                        childAt<FeedScreen.NewsItem>(0) {
                            isVisible()
                            matches {
                                withDescendant {
                                    withText(testNews.newsDescription)
                                }
                            }
                            click()
                        }
                    }
                }
            }
            step("Viewing clicked news") {
                NewsScreen {
                    newsDescription {
                        isVisible()
                        matches {
                            withText(testNews.newsDescription)
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