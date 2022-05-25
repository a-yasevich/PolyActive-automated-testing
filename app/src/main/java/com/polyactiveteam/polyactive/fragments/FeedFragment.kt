package com.polyactiveteam.polyactive.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.polyactiveteam.polyactive.MainActivity
import com.polyactiveteam.polyactive.R
import com.polyactiveteam.polyactive.adapters.NewsAdapter
import com.polyactiveteam.polyactive.databinding.FragmentFeedBinding
import com.polyactiveteam.polyactive.model.VkGroup
import com.polyactiveteam.polyactive.viewmodels.FeedViewModel
import kotlin.math.max

class FeedFragment : Fragment(R.layout.fragment_feed) {

    private lateinit var binding: FragmentFeedBinding
    private lateinit var mViewModel: FeedViewModel
    private var lastSelectedTab: Int? = 0
    private var lastVisibleNews: Int? = 0
    private val adapter = NewsAdapter()

    companion object {
        private const val LAST_TAB_SELECTED = "Tab"
        private const val LAST_NEWS_VISIBLE = "News"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.show()
        mViewModel = ViewModelProvider(this).get(FeedViewModel::class.java)
        (activity as MainActivity).supportActionBar?.title = getString(R.string.menu_title_feed)
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
            var totalTabsWith = 0
            var maxTabWith = 0
            for (i in 0 until tabLayout.tabCount) {
                val tabWidth = (tabLayout.getChildAt(0) as ViewGroup).getChildAt(i)!!.width
                totalTabsWith += tabWidth
                maxTabWith = max(maxTabWith, tabWidth)
            }
            val screenWidth = Resources.getSystem().displayMetrics.widthPixels
            if (totalTabsWith < screenWidth && screenWidth / tabLayout.tabCount >= maxTabWith) {
                tabLayout.tabMode = TabLayout.MODE_FIXED
            }
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
        lastSelectedTab = savedInstanceState?.getInt(LAST_TAB_SELECTED)
        lastVisibleNews = savedInstanceState?.getInt(LAST_NEWS_VISIBLE)

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        with(binding) {
            val manager = newsList.layoutManager as LinearLayoutManager
            outState.putInt(LAST_NEWS_VISIBLE, manager.findLastVisibleItemPosition())
            outState.putInt(LAST_TAB_SELECTED, tabLayout.selectedTabPosition)
        }
    }

    override fun onStart() {
        super.onStart()
        mViewModel.newsLiveData.observe(this, adapter::addAllItems)
    }

    override fun onResume() {
        super.onResume()
        with(binding) {
            tabLayout.selectTab(lastSelectedTab?.let { tabLayout.getTabAt(it) })
            lastVisibleNews?.let { newsList.layoutManager?.scrollToPosition(it) }
        }
    }

}