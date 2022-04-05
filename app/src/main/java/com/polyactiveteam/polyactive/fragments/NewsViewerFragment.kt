package com.polyactiveteam.polyactive.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.polyactiveteam.polyactive.R
import com.polyactiveteam.polyactive.databinding.FragmentFeedBinding
import com.polyactiveteam.polyactive.databinding.NewsViewerBinding
import com.polyactiveteam.polyactive.model.News


class NewsViewerFragment(private val news: News) : Fragment() {

    lateinit var binding: NewsViewerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = NewsViewerBinding.inflate(inflater, container, false)
        with(binding) {
            newsViewerNewsImage.setBackgroundResource(news.imageId)
            newsViewerHeader.text = news.header
            newsViewerDescription.text = news.newsDescription
            newsViewerLikeCount.text = news.likeCounter.toString()
            newsViewerDate.text =
                java.text.SimpleDateFormat.getDateInstance()
                    .format(java.util.Date(news.date)) // Wrong date converter!
        }
        return binding.root
    }

}