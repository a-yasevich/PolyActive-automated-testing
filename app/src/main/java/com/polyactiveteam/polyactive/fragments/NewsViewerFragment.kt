package com.polyactiveteam.polyactive.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.polyactiveteam.polyactive.databinding.FragmentNewsViewerBinding
import com.polyactiveteam.polyactive.model.News

class NewsViewerFragment(private val news: News) : Fragment() {

    lateinit var binding: FragmentNewsViewerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewsViewerBinding.inflate(inflater, container, false)
        with(binding) {
            Glide.with(this@NewsViewerFragment)
                .load(news.imageLink)
                .into(newsViewerNewsImage)
            newsViewerHeader.text = news.header
            newsViewerDescription.text = news.newsDescription
            newsViewerLikeCount.text = news.likeCounter.toString()
            newsViewerDate.text = news.date
        }
        return binding.root
    }

}