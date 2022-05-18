package com.polyactiveteam.polyactive.fragments

import android.annotation.SuppressLint
import android.content.Context
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
import com.polyactiveteam.polyactive.model.Group
import com.polyactiveteam.polyactive.viewmodels.FeedViewModel

class FeedFragment : Fragment(R.layout.fragment_feed) {

    private lateinit var binding: FragmentFeedBinding
    private lateinit var mViewModel: FeedViewModel
    private var selectedTab: Int? = 0
    private val adapter = NewsAdapter()

    companion object {
        private const val LAST_TAB_SELECTED = "tab"
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
        val groupsSet: Set<Group> =
            view.context.getSharedPreferences(ProfileFragment.GROUPS_KEY, Context.MODE_PRIVATE)
                .getStringSet(ProfileFragment.GROUPS_KEY, emptySet())
                ?.map { string -> Group.valueOf(string) }
                ?.toSet() ?: emptySet()
        val tabLayout = binding.tabLayout
        groupsSet.forEach {
            tabLayout.addTab(
                with(tabLayout.newTab()) {
                    setText(view.resources.getString(it.id))
                    setId(it.id)
                })
        }
        tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onTabSelected(tab: TabLayout.Tab) {
                adapter.changeType(Group.groupByID(tab.id))
                adapter.notifyDataSetChanged()
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
        selectedTab = savedInstanceState?.getInt(LAST_TAB_SELECTED)

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val manager =  binding.newsList.layoutManager as LinearLayoutManager
        manager.findFirstVisibleItemPosition()
        outState.putInt(LAST_TAB_SELECTED, binding.tabLayout.selectedTabPosition)
    }

    override fun onStart() {
        super.onStart()
        mViewModel.newsLiveData.observe(this, adapter::addAllItems)
    }

    override fun onResume() {
        super.onResume()
        val tabLayout = binding.tabLayout
        val selectedTab = selectedTab
        if (selectedTab != null) {
            tabLayout.selectTab(tabLayout.getTabAt(selectedTab))
        }
    }

}