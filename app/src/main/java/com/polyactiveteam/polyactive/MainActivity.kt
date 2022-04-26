package com.polyactiveteam.polyactive

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.polyactiveteam.polyactive.services.LanguageManager

class MainActivity : AppCompatActivity() {
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
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(LanguageManager.updateWithPersisted(base ?: return, "ru"))
    }
}