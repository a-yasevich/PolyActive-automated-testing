package com.polyactiveteam.polyactive.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.polyactiveteam.polyactive.R
import com.polyactiveteam.polyactive.adapters.NewsAdapter
import com.polyactiveteam.polyactive.databinding.FragmentFeedBinding
import com.polyactiveteam.polyactive.viewmodels.FeedViewModel

class FeedFragment : Fragment(R.layout.fragment_feed) {

    private lateinit var binding: FragmentFeedBinding
    lateinit var mViewModel: FeedViewModel
    private val adapter = NewsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = ViewModelProvider(this).get(FeedViewModel::class.java)
        val aBar = activity?.actionBar
        aBar?.title = "Новости"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        adapter.fragmentManager = parentFragmentManager
        binding = FragmentFeedBinding.inflate(inflater, container, false)
        binding.apply {
            newsList.layoutManager = LinearLayoutManager(context)
            newsList.adapter = adapter
        }
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        mViewModel.newsLiveData.observe(this, adapter::addAllItems)
    }
}