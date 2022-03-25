package com.polyactiveteam.polyactive

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView

class MainActivity : AppCompatActivity() {
    private val fragmentManager: FragmentManager = supportFragmentManager
    private val feedFragment: Fragment = FeedFragment()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val toast = Toast(applicationContext)
        setContentView(R.layout.activity_main)
        toast.setText("Application started successful!")
        toast.show()

        val bottomNavigation: BottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_profile -> {
                    //TODO
                }
                R.id.action_feed -> {
                    setFragment(feedFragment)
                    // сейчас при выборе ленты новостей она останется выбраной до конца
                    // поскольку при нажатии на другие кнопки снизу ничего не происходит
                }
                R.id.action_settings -> {
                    //TODO
                }
            }
            true
        }
    }

    private fun setFragment(fragment: Fragment) {
        fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit()
    }
}