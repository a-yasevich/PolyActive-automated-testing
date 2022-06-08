package com.polyactiveteam.polyactive.adapters

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.polyactiveteam.polyactive.R
import com.polyactiveteam.polyactive.databinding.NewsItemBinding
import com.polyactiveteam.polyactive.model.News
import com.polyactiveteam.polyactive.model.VkGroup
import java.net.URL
import java.util.*

class NewsAdapter() : RecyclerView.Adapter<NewsAdapter.NewsHolder>() {

    private val news = mutableMapOf<VkGroup, MutableSet<News>>()
    private val sortedNews = mutableMapOf<VkGroup, MutableList<News>>()
    private var currentNewsType = VkGroup.GROUP_ALL
    lateinit var navController: NavController

    fun getNewsType(): VkGroup {
        return currentNewsType
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsHolder {
        return NewsHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.news_item,
                parent,
                false
            ), this.navController
        )
    }

    override fun onBindViewHolder(holder: NewsHolder, position: Int) {
        holder.bind(sortedNews[currentNewsType]!![position])
    }

    override fun getItemCount(): Int {
        return sortedNews[currentNewsType]?.size ?: 0
    }

    fun changeType(group: VkGroup) {
        currentNewsType = group
    }

    fun addAllItems(news: Map<VkGroup, MutableSet<News>>) {
        for (entry: Map.Entry<VkGroup, MutableSet<News>> in news) {
            addGroupNews(entry.key, entry.value)
        }
        addAllNews()
    }

    fun updateGroup(group: VkGroup, downloadedNews: MutableSet<News>) {
        addGroupNews(group, downloadedNews)
        addAllNews()
    }

    private fun addGroupNews(group: VkGroup, news: MutableSet<News>) {
        if (this.news[group] == null) {
            this.news[group] = Collections.emptySet()
        }
        this.news[group] = this.news[group]!!.union(news) as MutableSet<News>
        this.sortedNews[group] = this.news[group]?.toMutableList()!!
        this.sortedNews[group]!!.sortWith { o1, o2 ->
            o2.dateLong.compareTo(
                o1.dateLong
            )
        }
    }

    private fun addAllNews() {
        val allNews = this.news.flatMap { (_, value) -> value }.sortedWith { o1, o2 ->
            o2.dateLong.compareTo(
                o1.dateLong
            )
        }
        this.sortedNews[VkGroup.GROUP_ALL] = allNews as MutableList<News>
    }

    class NewsHolder(itemView: View, private val navController: NavController) :
        RecyclerView.ViewHolder(itemView) {

        private val binding = NewsItemBinding.bind(itemView)

        fun bind(news: News) = with(binding) {
            val image: Bitmap? = news.getImageBitmap()
            if (image != null) {
                newsCardNewsImage.setImageBitmap(image)
            }

            newsCardNewsHeader?.text = news.header
            newsCardSmallDescription.text = news.newsDescription
            newsCardNewsDate.text = news.date
            binding.newsCard.setOnClickListener {
                val bundle: Bundle = bundleOf(
                    "id" to news.id,
                    "imageURL" to news.imageURL,
                    "image" to news.image,
                    "header" to news.header,
                    "newsDescription" to news.newsDescription,
                    "date" to news.date,
                    "dateLong" to news.dateLong,
                    "likeCounter" to news.likeCounter,
                )
                navController.navigate(R.id.action_feed_fragment_to_news_viewer, bundle)
            }
        }
    }
}