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
import com.polyactiveteam.polyactive.model.Group
import com.polyactiveteam.polyactive.model.News

class NewsAdapter() : RecyclerView.Adapter<NewsAdapter.NewsHolder>() {

    private val newsList = mutableListOf<News>()
    private var crutchList = newsList
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
        holder.bind(crutchList[position])
    }

    override fun getItemCount(): Int {
        return crutchList.size
    }

    fun changeType(group: Group) {
        crutchList = newsList.filter { group == Group.ALL || it.groupType == group }
            .toMutableList()
    }

    fun addAllItems(news: ArrayList<News>) {
        newsList.addAll(news)
    }
}