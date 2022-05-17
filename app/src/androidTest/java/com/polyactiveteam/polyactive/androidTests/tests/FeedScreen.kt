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
        itemType(::Item)
    })

    internal class Item(parent: Matcher<View>) : KRecyclerItem<Item>(parent) {
        val header = KTextView(parent) { withId(R.id.news_card__news_header) }
    }
}