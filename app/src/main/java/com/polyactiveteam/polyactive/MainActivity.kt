package com.polyactiveteam.polyactive

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.polyactiveteam.polyactive.fragments.FeedFragment
import com.polyactiveteam.polyactive.fragments.SettingsFragment

class MainActivity : AppCompatActivity() {

    private val fragmentManager: FragmentManager = supportFragmentManager
    private val feedFragment: Fragment = FeedFragment()
    private val settingFragment: Fragment = SettingsFragment()
    private val profileFragment = ProfileFragment()

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val bottomNavigation: BottomNavigationView = findViewById(R.id.bottom_navigation)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        navController = navHostFragment.navController

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.fragment_login -> bottomNavigation.visibility = View.GONE
                R.id.fragment_registration -> bottomNavigation.visibility = View.GONE
                else -> bottomNavigation.visibility = View.VISIBLE
            }
        }

        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_profile -> {
                    //Посмотрите в navigation_graph - комментарий для перехода в fragment_login
                    //Переместить строку из fragment_settings в fragment_profile
                    setFragment(profileFragment)
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