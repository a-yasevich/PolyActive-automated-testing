package com.polyactiveteam.polyactive.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.polyactiveteam.polyactive.model.News
import com.polyactiveteam.polyactive.model.VkGroup
import com.polyactiveteam.polyactive.services.NewsService

class FeedViewModel(application: Application) : AndroidViewModel(application) {

    val newsLiveData = MutableLiveData<Map<VkGroup, List<News>>>()

    init {
        val plugMap = mapOf(
            VkGroup.ADAPTERS to NewsService.getPostsFromGroup(VkGroup.ADAPTERS, 2),
            VkGroup.PROF to NewsService.getPostsFromGroup(VkGroup.PROF, 2),
            VkGroup.STUD_BRIGADES to NewsService.getPostsFromGroup(VkGroup.STUD_BRIGADES, 2)
        )
        newsLiveData.value = plugMap
    }
}