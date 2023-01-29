package com.polyactiveteam.polyactive.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.polyactiveteam.polyactive.MainActivity
import com.polyactiveteam.polyactive.R
import com.polyactiveteam.polyactive.adapters.NewsAdapter
import com.polyactiveteam.polyactive.databinding.FragmentFeedBinding
import com.polyactiveteam.polyactive.model.News
import com.polyactiveteam.polyactive.model.VkGroup
import com.polyactiveteam.polyactive.room.NewsRepository
import com.polyactiveteam.polyactive.services.NewsService
import com.polyactiveteam.polyactive.utils.NetworkUtils
import com.polyactiveteam.polyactive.viewmodels.FeedViewModel
import kotlin.math.max

class FeedFragment : Fragment() {

    private lateinit var binding: FragmentFeedBinding
    private lateinit var mViewModel: FeedViewModel
    private val adapter = NewsAdapter()
    private lateinit var news: NewsRepository

    companion object {
        private const val LAST_TAB_SELECTED = "Tab"
        private const val LAST_NEWS_VISIBLE = "News"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentFeedBinding.inflate(layoutInflater)
        news = NewsRepository(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        (requireActivity() as AppCompatActivity).supportActionBar?.show()
        adapter.navController = findNavController()
        binding = FragmentFeedBinding.inflate(inflater, container, false)
        binding.apply {
            newsList.layoutManager = LinearLayoutManager(context)
            newsList.adapter = adapter
            swipeContainer?.setOnRefreshListener {
                val map: MutableMap<VkGroup, MutableSet<News>>? = mViewModel.getLiveData().value//FIXME
                if (adapter.getNewsType() != VkGroup.GROUP_ALL) {
                    val postsFromGroup
                            = NewsService.getPostsFromGroup(adapter.getNewsType(), 10, news)
                    adapter.updateGroup(adapter.getNewsType(), postsFromGroup)
                } else {
                    val news = NewsService.getPostsFromAllGroups(10, news)
                    adapter.addAllItems(news)
                }
                swipeContainer.isRefreshing = false
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.show()
        val supportActionBar = (activity as MainActivity).supportActionBar
        if (supportActionBar != null) {
            supportActionBar.title = getString(R.string.menu_title_feed)
            supportActionBar.setDisplayHomeAsUpEnabled(false)
            supportActionBar.setHomeButtonEnabled(false)
        }

        if (!NetworkUtils.isInternetAvailable(requireContext())) {
            Toast.makeText(context, "Network fail", Toast.LENGTH_SHORT).show()
            return
        }

        mViewModel = ViewModelProvider(requireActivity()).get(FeedViewModel::class.java)
        val groupsSet: Set<VkGroup> =
            view.context.getSharedPreferences(
                ProfileFragment.USER_PREFS_FILE_NAME,
                Context.MODE_PRIVATE
            ).getStringSet(ProfileFragment.GROUPS_KEY, emptySet())
                ?.map { string -> VkGroup.valueOf(string) }
                ?.toSet() ?: emptySet()

        val tabLayout = binding.tabLayout

        groupsSet.forEach {
            tabLayout.addTab(
                tabLayout.newTab().apply {
                    text = view.resources.getString(it.stringResId)
                    id = it.stringResId
                })
        }

        tabLayout.addOnLayoutChangeListener { _, _, _, _, _, _, _, _, _ ->
            setupTabLayoutMode(tabLayout)
        }

        tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onTabSelected(tab: TabLayout.Tab) {
                adapter.changeType(VkGroup.groupByID(tab.id))
                adapter.notifyDataSetChanged()
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        mViewModel.getLiveData().observe(viewLifecycleOwner, adapter::addAllItems)
    }

    private fun setupTabLayoutMode(tabLayout: TabLayout) {
        var maxTabWith = 0
        val tabViewGroup = tabLayout.getChildAt(0) as ViewGroup
        for (i in 0 until tabLayout.tabCount) {
            val tabWidth = tabViewGroup.getChildAt(i).width
            maxTabWith = max(maxTabWith, tabWidth)
        }
        val screenWidth = Resources.getSystem().displayMetrics.widthPixels
        if (maxTabWith * tabLayout.tabCount < screenWidth) {
            tabLayout.tabMode = TabLayout.MODE_FIXED
        }
    }

    override fun onStop() {
        super.onStop()
        if (!NetworkUtils.isInternetAvailable(requireContext())) {
            return
        }
        with(binding) {
            val manager = newsList.layoutManager as LinearLayoutManager?
            manager?.let {
                mViewModel.newsVisible = max(
                    it.findFirstCompletelyVisibleItemPosition(),
                    it.findFirstVisibleItemPosition()
                )
            }
            mViewModel.tabSelected = tabLayout.selectedTabPosition
        }
    }

    override fun onResume() {
        super.onResume()
        if (!NetworkUtils.isInternetAvailable(requireContext())) {
            return
        }
        with(binding) {
            mViewModel.tabSelected?.let { tabLayout.selectTab(tabLayout.getTabAt(it)) }
            mViewModel.newsVisible?.let { newsList.layoutManager?.scrollToPosition(it) }
        }
    }

}