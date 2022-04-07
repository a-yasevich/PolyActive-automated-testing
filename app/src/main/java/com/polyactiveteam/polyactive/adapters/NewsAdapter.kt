package com.polyactiveteam.polyactive.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.polyactiveteam.polyactive.R
import com.polyactiveteam.polyactive.databinding.NewsItemBinding
import com.polyactiveteam.polyactive.fragments.NewsViewerFragment
import com.polyactiveteam.polyactive.model.News

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.NewsHolder>() {

    private val newsList = ArrayList<News>()
    lateinit var fragmentManager: FragmentManager

    class NewsHolder(itemView: View, private val fragmentManager: FragmentManager) :
        RecyclerView.ViewHolder(itemView) {

        private val binding = NewsItemBinding.bind(itemView)

        fun bind(news: News) = with(binding) {
            newsCardNewsImage.setBackgroundResource(news.imageId)
            newsCardNewsHeader.text = news.header
            newsCardSmallDescription.text = news.newsDescription
            newsCardNewsDate.text =
                java.text.SimpleDateFormat.getDateInstance()
                    .format(java.util.Date(news.date)) // Wrong date converter!
            binding.newsCard.setOnClickListener {
                setFragment(NewsViewerFragment(news))
            }
        }

        private fun setFragment(fragment: Fragment) {
            fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit()
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
        holder.bind(newsList[position])
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    fun addAllItems(news: ArrayList<News>) {
        newsList.addAll(news)
    }
}