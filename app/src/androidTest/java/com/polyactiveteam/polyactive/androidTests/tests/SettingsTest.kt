package com.polyactiveteam.polyactive.androidTests.tests

import android.content.res.Configuration
import androidx.test.platform.app.InstrumentationRegistry
import com.polyactiveteam.polyactive.MainActivity
import com.polyactiveteam.polyactive.R
import com.polyactiveteam.polyactive.androidTests.screens.*
import org.junit.Test
import java.util.*

class SettingsTest : BaseTest<MainActivity>(MainActivity::class.java) {
    override fun onSetup() {
    }

    @Test
    fun switchLanguage() {
        run {
            step("Logging in") {
                LoginScreen {
                    loginButton {
                        isVisible()
                        click()
                    }
                }
            }
            step("Go to settings") {
                FeedScreen {
                    BottomNavigation {
                        settingsBottomNavItem {
                            isVisible()
                            click()
                        }
                    }
                }
            }
            step("Change language to english") {
                SettingsScreen {
                    englishButton {
                        isVisible()
                        click()
                    }
                }
            }
            step("Check that screen has been translated") {
                SettingsScreen {
                    languageTextFiled {
                        isVisible()
                        hasText(getResourceString(R.string.language_field, "en"))
                    }
                }
            }
            step("Go to profile to check the translation") {
                SettingsScreen {
                    BottomNavigation.profileBottomNavItem {
                        isVisible()
                        click()
                    }
                }
                ProfileScreen {
                    exitButton {
                        isVisible()
                        hasText(getResourceString(R.string.exit, "en"))
                    }
                    profButton {
                        isVisible()
                        hasDescendant { withText(getResourceString(R.string.prof_name, "en")) }
                    }
                    brigadesButton {
                        isVisible()
                        hasDescendant { withText(getResourceString(R.string.students_brigades_name, "en")) }
                    }
                    adaptersButton {
                        isVisible()
                        hasDescendant { withText(getResourceString(R.string.adapters_name, "en")) }
                    }
                }
            }
        }
    }

    private fun getResourceString(id: Int, localeName: String): String {
        val config = Configuration()
        val locale = Locale(localeName)
        config.locale = locale
        val resources = InstrumentationRegistry.getInstrumentation().targetContext.resources
        resources.updateConfiguration(config, resources.displayMetrics)
        return resources.getString(id)
    }
}