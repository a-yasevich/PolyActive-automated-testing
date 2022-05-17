package com.polyactiveteam.polyactive.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.polyactiveteam.polyactive.R
import com.polyactiveteam.polyactive.databinding.FragmentNewsViewerBinding
import com.polyactiveteam.polyactive.model.News

class NewsViewerFragment(private val news: News) : Fragment() {

    private lateinit var binding: FragmentNewsViewerBinding
    private lateinit var bottomNavigation: BottomNavigationView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bottomNavigation = requireActivity().findViewById(R.id.bottom_navigation)
        bottomNavigation.visibility = View.GONE
        binding = FragmentNewsViewerBinding.inflate(inflater, container, false)
        with(binding) {
            newsViewerNewsImage.setBackgroundResource(news.imageId)
            newsViewerHeader.text = news.header
            newsViewerDescription.text = news.newsDescription
            newsViewerLikeCount.text = news.likeCounter.toString()
            newsViewerDate.text = java.text.SimpleDateFormat.getDateInstance()
                .format(java.util.Date(news.date)) // Wrong date converter!
            newsViewerToolbar.setNavigationOnClickListener {
                getBack()
            }
        }
        return binding.root
    }

    override fun onDetach() {
        super.onDetach()
        //getBack()
    }

    private fun getBack() {
        parentFragmentManager.popBackStack()
        bottomNavigation.visibility = View.VISIBLE
    }
}