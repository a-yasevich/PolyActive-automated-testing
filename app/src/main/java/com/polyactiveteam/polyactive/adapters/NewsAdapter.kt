package com.polyactiveteam.polyactive.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.polyactiveteam.polyactive.R
import com.polyactiveteam.polyactive.databinding.NewsItemBinding
import com.polyactiveteam.polyactive.fragments.NewsViewerFragment
import com.polyactiveteam.polyactive.model.News
import com.polyactiveteam.polyactive.model.VkGroup

class NewsAdapter() : RecyclerView.Adapter<NewsAdapter.NewsHolder>() {

    private val news = mutableMapOf<VkGroup, List<News>>()
    private var currentNewsType = VkGroup.GROUP_ALL
    lateinit var fragmentManager: FragmentManager

    class NewsHolder(itemView: View, private val fragmentManager: FragmentManager) :
        RecyclerView.ViewHolder(itemView) {

        private val binding = NewsItemBinding.bind(itemView)

        fun bind(news: News) = with(binding) {
            if (news.imageLink != null) {
                Glide.with(itemView)
                    .load(news.imageLink)
                    .into(newsCardNewsImage)

            }
            newsCardNewsHeader.text = news.header
            newsCardSmallDescription.text = news.newsDescription
            newsCardNewsDate.text = news.date
            binding.newsCard.setOnClickListener {
                setFragment(NewsViewerFragment(news))
            }
        }

        private fun setFragment(fragment: Fragment) {
            fragmentManager.beginTransaction()
                .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                .replace(R.id.fragment_container, fragment)
                .addToBackStack("feed_fragment")
                .commit()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsHolder {
        return NewsHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.news_item,
                parent,
                false
            ), this.fragmentManager
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
}