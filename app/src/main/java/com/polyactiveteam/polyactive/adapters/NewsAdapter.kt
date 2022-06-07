package com.polyactiveteam.polyactive.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.polyactiveteam.polyactive.R
import com.polyactiveteam.polyactive.databinding.NewsItemBinding
import com.polyactiveteam.polyactive.model.News
import com.polyactiveteam.polyactive.model.VkGroup

class NewsAdapter() : RecyclerView.Adapter<NewsAdapter.NewsHolder>() {

    private val news = mutableMapOf<VkGroup, List<News>>()
    private var currentNewsType = VkGroup.GROUP_ALL
    lateinit var navController: NavController

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
        holder.bind(news[currentNewsType]!![position])
    }

    override fun getItemCount(): Int {
        return news[currentNewsType]?.size ?: 0
    }

    fun changeType(group: VkGroup) {
        currentNewsType = group
    }

    fun addAllItems(news: Map<VkGroup, List<News>>) {
        this.news.putAll(news)
        val allNews = news.flatMap { (_, value) -> value }.sortedWith { o1, o2 ->
            o2.date.compareTo(
                o1.date
            )
        }
        this.news[VkGroup.GROUP_ALL] = allNews
    }

    class NewsHolder(itemView: View, private val navController: NavController) :
        RecyclerView.ViewHolder(itemView) {

        private val binding = NewsItemBinding.bind(itemView)

        fun bind(news: News) = with(binding) {
            if (news.imageLink != null) {
                Glide.with(itemView)
                    .load(news.imageLink)
                    .into(newsCardNewsImage)

            }
            newsCardSmallDescription.text = news.newsDescription
            newsCardNewsDate.text = news.date
            binding.newsCard.setOnClickListener {
                val bundle: Bundle = bundleOf(
                    "imageLink" to news.imageLink,
                    "newsDescription" to news.newsDescription,
                    "date" to news.date,
                    "likeCounter" to news.likeCounter,
                )
                navController.navigate(R.id.action_feed_fragment_to_news_viewer, bundle)
            }
        }
    }
}