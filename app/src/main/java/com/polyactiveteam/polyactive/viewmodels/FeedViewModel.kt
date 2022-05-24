package com.polyactiveteam.polyactive.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.polyactiveteam.polyactive.model.News
import com.polyactiveteam.polyactive.model.VkGroups
import com.polyactiveteam.polyactive.services.NewsService

class FeedViewModel(application: Application) : AndroidViewModel(application) {

    val newsLiveData = MutableLiveData<ArrayList<News>>()

    init {
        newsLiveData.apply {
            val plugList = ArrayList<News>()
            plugList.addAll(NewsService.getPostsFromGroup(VkGroups.ADAPTERS, 2))
            plugList.addAll(NewsService.getPostsFromGroup(VkGroups.PROF, 2))
            plugList.addAll(NewsService.getPostsFromGroup(VkGroups.STUD_BRIGADES, 2))
            newsLiveData.value = plugList
        }
    }
}