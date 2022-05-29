package com.polyactiveteam.polyactive

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.polyactiveteam.polyactive.databinding.ActivityMainBinding
import com.polyactiveteam.polyactive.databinding.FragmentFeedBinding
import com.polyactiveteam.polyactive.services.SettingsManager

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment

        navController = navHostFragment.navController

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_profile -> {
                    navController.navigate(R.id.profile_fragment)
                }
                R.id.action_feed -> {
                    navController.navigate(R.id.feed_fragment)
                }
                R.id.action_settings -> {
                    navController.navigate(R.id.settings_fragment)
                }
            }
            true
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.fragment_login -> binding.bottomNavigation.visibility = View.GONE
                else -> binding.bottomNavigation.visibility = View.VISIBLE
            }
        }
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(
            SettingsManager.updateWithPersisted(
                base ?: return,
                "ru",
                false
            )
        )
    }
}