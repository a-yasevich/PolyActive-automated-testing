package com.polyactiveteam.polyactive.services

import com.polyactiveteam.polyactive.model.Group
import com.polyactiveteam.polyactive.model.News
import com.polyactiveteam.polyactive.model.VkGroup
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject

class NewsService {
    companion object {
        private const val TOKEN =
            "41106243411062434110624347416cfb114411041106243236e20904bb4a66937d4aaaa"
        private const val VERSION = 5.131
        private const val getPostsUrl = "https://api.vk.com/method/wall.get"
        private val client = OkHttpClient()

        fun getPostsFromGroup(group: Group, postCount: Int): MutableSet<News> {

            val url: String = getPostsUrl.toHttpUrlOrNull()!!
                .newBuilder()
                .addQueryParameter("owner_id", (-group.getId()).toString())
                .addQueryParameter("count", postCount.toString())
                .addQueryParameter("v", VERSION.toString())
                .addQueryParameter("access_token", TOKEN)
                .build()
                .toString()

            val request = Request
                .Builder()
                .url(url)
                .build()
            val news = HashSet<News>()
            val thread = Thread {
                client.newCall(request).execute().use {
                    val jsonArray: JSONArray = JSONObject(it.body!!.string())
                        .getJSONObject("response")
                        .getJSONArray("items")
                    for (i in 0 until jsonArray.length()) {
                        news.add(News.getNewsFromJson(jsonArray.get(i) as JSONObject))
                    }
                }
            }
            thread.start()
            thread.join()
            return news
        }

        fun getPostsFromAllGroups(count: Int): MutableMap<VkGroup, MutableSet<News>> {
            return mutableMapOf(
                VkGroup.ADAPTERS to getPostsFromGroup(VkGroup.ADAPTERS, count),
                VkGroup.PROF to getPostsFromGroup(VkGroup.PROF, count),
                VkGroup.STUD_BRIGADES to getPostsFromGroup(VkGroup.STUD_BRIGADES, count)
            )
        }
    }
}
