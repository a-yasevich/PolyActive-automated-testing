package com.polyactiveteam.polyactive.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.polyactiveteam.polyactive.R
import com.polyactiveteam.polyactive.adapters.NewsAdapter
import com.polyactiveteam.polyactive.databinding.FragmentFeedBinding
import com.polyactiveteam.polyactive.model.News

class FeedFragment : Fragment(R.layout.fragment_feed) {

    lateinit var binding: FragmentFeedBinding
    private val adapter = NewsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        adapter.fragmentManager = parentFragmentManager
        binding = FragmentFeedBinding.inflate(inflater, container, false)
        initNewsFeed()
        return binding.root
    }

    private fun initNewsFeed() {
        binding.apply {
            newsList.layoutManager = LinearLayoutManager(context)
            adapter.addItem(
                News(
                    R.drawable.news1,
                    "Новый RecycleView",
                    "Команда PolyActive добавила RecycleView к своему проекту",
                    1648329900,
                    0
                )
            ) // Заглушка
            adapter.addItem(
                News(
                    R.drawable.heart_plug,
                    "Крутая новость",
                    "Новость действительно крутая",
                    1648375200,
                    0
                )
            ) // Заглушка
            newsList.adapter = adapter
        }
    }
}