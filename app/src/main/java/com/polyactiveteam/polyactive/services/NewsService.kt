package com.polyactiveteam.polyactive.services

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.polyactiveteam.polyactive.model.Group
import com.polyactiveteam.polyactive.model.News
import com.polyactiveteam.polyactive.model.VkGroup
import com.polyactiveteam.polyactive.room.NewsEntity
import com.polyactiveteam.polyactive.room.NewsRepository
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.Executors

class NewsService {
    companion object {
        private const val TOKEN =
            "41106243411062434110624347416cfb114411041106243236e20904bb4a66937d4aaaa"
        private const val VERSION = 5.131
        private const val getPostsUrl = "https://api.vk.com/method/wall.get"
        private val client = OkHttpClient()
        private val service = Executors.newFixedThreadPool(4)

        fun getPostsFromGroup(
            group: Group,
            postCount: Int,
            newsRepository: NewsRepository
        ): MutableSet<News> {

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
            newsRepository.insertNews(news.toList().toEntity(group.getId()))
            return news
        }

        fun getPostsFromAllGroups(
            count: Int,
            news: NewsRepository
        ): MutableMap<VkGroup, MutableSet<News>> {
            return mutableMapOf(
                VkGroup.ADAPTERS to getPostsFromGroup(VkGroup.ADAPTERS, count, news),
                VkGroup.PROF to getPostsFromGroup(VkGroup.PROF, count, news),
                VkGroup.STUD_BRIGADES to getPostsFromGroup(VkGroup.STUD_BRIGADES, count, news)
            )
        }

        fun getPostsFromAllGroupsFromDB(news: NewsRepository): MutableMap<VkGroup, MutableSet<News>> {
            return mutableMapOf(
                VkGroup.ADAPTERS to newsFromDBInSet(news, VkGroup.ADAPTERS.getId()),
                VkGroup.PROF to newsFromDBInSet(news, VkGroup.PROF.getId()),
                VkGroup.STUD_BRIGADES to newsFromDBInSet(news, VkGroup.STUD_BRIGADES.getId())
            )
        }

        private fun newsFromDBInSet(news: NewsRepository, groupId: Int): MutableSet<News> {
            return news.getAllNewsFrom(groupId).toNews().toMutableSet()
        }

        private fun List<News>.toEntity(groupId: Int): List<NewsEntity> {
            return this.map { news ->
                NewsEntity(
                    id = news.id,
                    image = service.submit<Bitmap> {
                        return@submit BitmapFactory.decodeStream(
                            news.imageURL?.openConnection()?.getInputStream()
                        )
                    }.get(),
                    header = news.header,
                    newsDescription = news.newsDescription,
                    date = news.date,
                    dateLong = news.dateLong,
                    likeCounter = news.likeCounter,
                    groupId = groupId
                )
            }
        }

        private fun List<NewsEntity>.toNews(): List<News> {
            return this.map { newsEntity ->
                News(
                    id = newsEntity.id,
                    imageURL = null,
                    image = newsEntity.image,
                    header = newsEntity.header,
                    newsDescription = newsEntity.newsDescription,
                    date = newsEntity.date,
                    dateLong = newsEntity.dateLong,
                    likeCounter = newsEntity.likeCounter
                )
            }
        }
    }
}
