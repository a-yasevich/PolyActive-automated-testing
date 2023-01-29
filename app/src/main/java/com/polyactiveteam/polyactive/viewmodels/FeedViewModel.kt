package com.polyactiveteam.polyactive.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.polyactiveteam.polyactive.model.News
import com.polyactiveteam.polyactive.model.VkGroup
import com.polyactiveteam.polyactive.room.NewsRepository
import com.polyactiveteam.polyactive.services.NewsService

class FeedViewModel(application: Application) : AndroidViewModel(application) {
    var tabSelected: Int? = null
    var newsVisible: Int? = null
    private val newsLiveData = MutableLiveData<MutableMap<VkGroup, MutableSet<News>>>()
    private val news = NewsRepository(application.applicationContext)

    init {
        val plugMap = if (news.getCount() == 0) {
            NewsService.getPostsFromAllGroups(5, news)
        } else {
            NewsService.getPostsFromAllGroupsFromDB(news)
        }

        newsLiveData.value = plugMap
    }

    fun getLiveData(): LiveData<MutableMap<VkGroup, MutableSet<News>>> {
        return newsLiveData
    }
}