package com.polyactiveteam.polyactive.androidTests.screens

import android.view.View
import android.widget.TextView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import com.agoda.kakao.recycler.KRecyclerItem
import com.agoda.kakao.recycler.KRecyclerView
import com.agoda.kakao.screen.Screen
import com.agoda.kakao.text.KTextView
import com.polyactiveteam.polyactive.R
import com.polyactiveteam.polyactive.model.News
import org.hamcrest.Matcher

object FeedScreen : Screen<FeedScreen>() {
    val feedList: KRecyclerView = KRecyclerView({
        withId(R.id.news_list)
    }, itemTypeBuilder = {
        itemType(FeedScreen::NewsItem)
    })

    class NewsItem(parent: Matcher<View>) : KRecyclerItem<NewsItem>(parent) {
        val header = KTextView(parent) { withId(R.id.news_card__news_header) }
        val description = KTextView(parent) { withId(R.id.news_card_small_description) }

        fun newsObj() = News(0, header.text(), description.text(), 0, 0)

        private fun KTextView.text(): String {
            var text = ""
            this.view.interaction.perform(
                (object : ViewAction {
                    override fun getConstraints(): Matcher<View> {
                        return isAssignableFrom(TextView::class.java)
                    }

                    override fun getDescription(): String {
                        return "Text of the view"
                    }

                    override fun perform(uiController: UiController, view: View) {
                        val tv = view as TextView
                        text = tv.text.toString()
                    }
                })
            )
            return text
        }

    }
}