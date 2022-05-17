package com.polyactiveteam.polyactive.androidTests.tests

import android.view.View
import com.agoda.kakao.recycler.KRecyclerItem
import com.agoda.kakao.recycler.KRecyclerView
import com.agoda.kakao.screen.Screen
import com.agoda.kakao.text.KTextView
import com.polyactiveteam.polyactive.R
import org.hamcrest.Matcher

object FeedScreen : Screen<FeedScreen>() {
    val feedList: KRecyclerView = KRecyclerView({
        withId(R.id.news_list)
    }, itemTypeBuilder = {
        itemType(::NewsItem)
    })

    internal class NewsItem(parent: Matcher<View>) : KRecyclerItem<NewsItem>(parent) {
        val header = KTextView(parent) { withId(R.id.news_card__news_header) }
    }
}