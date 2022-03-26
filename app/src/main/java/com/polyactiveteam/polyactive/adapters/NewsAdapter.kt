package com.polyactiveteam.polyactive.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.polyactiveteam.polyactive.R
import com.polyactiveteam.polyactive.databinding.NewsItemBinding
import com.polyactiveteam.polyactive.model.News

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.NewsHolder>() {

    private val newsList = ArrayList<News>()

    class NewsHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding = NewsItemBinding.bind(itemView)

        fun bind(news: News) = with(binding) {
            newsCardNewsImage.setBackgroundResource(news.imageId)
            newsCardNewsHeader.text = news.header
            newsCardSmallDescription.text = news.newsDescription
            newsCardNewsDate.text =
                java.text.SimpleDateFormat.getDateInstance()
                    .format(java.util.Date(news.date)) // Wrong date converter!
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsHolder {
        return NewsHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.news_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: NewsHolder, position: Int) {
        holder.bind(newsList[position])
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    fun addItem(news: News) {
        newsList.add(news)
    }
}