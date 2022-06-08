package com.polyactiveteam.polyactive.fragments

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.polyactiveteam.polyactive.MainActivity
import com.polyactiveteam.polyactive.R
import com.polyactiveteam.polyactive.databinding.FragmentNewsViewerBinding
import com.polyactiveteam.polyactive.model.News


class NewsViewerFragment() : Fragment() {

    private lateinit var binding: FragmentNewsViewerBinding
    private lateinit var bottomNavigation: BottomNavigationView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.setHasOptionsMenu(true)
        val supportActionBar = (activity as MainActivity).supportActionBar
        if (supportActionBar != null) {
            supportActionBar.title = getString(R.string.menu_title_news_viewer)
            supportActionBar.setDisplayHomeAsUpEnabled(true)
            supportActionBar.setHomeButtonEnabled(true)
        }
        bottomNavigation = requireActivity().findViewById(R.id.bottom_navigation)
        bottomNavigation.visibility = View.GONE
        binding = FragmentNewsViewerBinding.inflate(inflater, container, false)
        val news = News(
            arguments?.getString("imageLink"),
            arguments?.getString("newsDescription")!!,
            arguments?.getString("date")!!,
            arguments?.getInt("likeCounter")!!
        )
        with(binding) {
            if (news.imageLink != null) {
                Glide.with(this@NewsViewerFragment)
                    .load(news.imageLink)
                    .into(newsViewerNewsImage)
            }
            newsViewerDescription.text = news.newsDescription
            newsViewerLikeCount.text = news.likeCounter.toString()
            newsViewerDate.text = news.date
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val textView = requireActivity().findViewById(R.id.news_viewer__description) as TextView
        textView.movementMethod = ScrollingMovementMethod.getInstance()
        requireView().setOnKeyListener(View.OnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_UP) {
                getBack()
                return@OnKeyListener true
            }
            false
        })
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            getBack()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getBack() {
        bottomNavigation.visibility = View.VISIBLE
        val supportActionBar = (activity as MainActivity).supportActionBar
        if (supportActionBar != null) {
            supportActionBar.title = getString(R.string.menu_title_feed)
            supportActionBar.setDisplayHomeAsUpEnabled(false)
            supportActionBar.setHomeButtonEnabled(false)
        }
        findNavController().navigate(R.id.news_viewer_fragment_to_feed)
    }
}