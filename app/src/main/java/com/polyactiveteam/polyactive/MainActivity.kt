package com.polyactiveteam.polyactive

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.polyactiveteam.polyactive.fragments.FeedFragment
import com.polyactiveteam.polyactive.fragments.SettingFragment

class MainActivity : AppCompatActivity() {

    // С binding почему-то не работает bottomNavigation
    // private lateinit var binding: ActivityMainBinding
    private val fragmentManager: FragmentManager = supportFragmentManager
    private val feedFragment: Fragment = FeedFragment()
    private val settingFragment: Fragment = SettingFragment()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)
        val bottomNavigation: BottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_profile -> {
                    //TODO
                }
                R.id.action_feed -> {
                    setFragment(feedFragment)
                }
                R.id.action_settings -> {
                    setFragment(settingFragment)
                }
            }
            true
        }
    }

    private fun setFragment(fragment: Fragment) {
        fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit()
    }
}