package com.polyactiveteam.polyactive.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.polyactiveteam.polyactive.R
import com.polyactiveteam.polyactive.model.Group
import com.polyactiveteam.polyactive.model.News

class FeedViewModel(application: Application) : AndroidViewModel(application) {

    val newsLiveData = MutableLiveData<ArrayList<News>>()

    init {
        newsLiveData.apply {
            val plugList = ArrayList<News>()
            plugList.add(
                News(
                    R.drawable.ic_news_plug,
                    Group.BRIGADES,
                    "Новый RecycleView",
                    "Команда PolyActive добавила RecycleView к своему проекту",
                    1648329900,
                    0
                )
            )
            plugList.add(
                News(
                    R.drawable.ic_russia,
                    Group.PROF,
                    "Русские вперёд",
                    "Новость действительно крутая",
                    1648375200,
                    0
                )
            )
            plugList.add(
                News(
                    R.drawable.ic_uk,
                    Group.PROF,
                    "Крутая новость",
                    "Новость действительно крутая",
                    1648375200,
                    0
                )
            )
            plugList.add(
                News(
                    R.drawable.ic_heart_plug,
                    Group.PROF,
                    "Крутая новость",
                    "Новость действительно крутая",
                    1648375200,
                    0
                )
            )
            newsLiveData.value = plugList
        }
    }
}