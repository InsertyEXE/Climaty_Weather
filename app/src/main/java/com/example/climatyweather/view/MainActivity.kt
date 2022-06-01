package com.example.climatyweather.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.example.climatyweather.R
import com.example.climatyweather.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

private lateinit var binding: ActivityMainBinding
private lateinit var homeFragment: HomeFragment
private lateinit var searchFragment: SearchFragment
private lateinit var favoriteFragment: FavoriteFragment

private var currentFragment: Fragment? = null

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        homeFragment = HomeFragment()
        searchFragment = SearchFragment()
        favoriteFragment = FavoriteFragment()

        binding.mainNavBottom.setOnNavigationItemSelectedListener(this)
        binding.mainNavBottom.selectedItemId = R.id.menu_home

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.menu_home -> {
                if (currentFragment == homeFragment) return false
                currentFragment = homeFragment
            }
            R.id.menu_search -> {
                if (currentFragment == searchFragment) return false
                currentFragment = searchFragment
            }
            R.id.menu_fav -> {
                if (currentFragment == favoriteFragment) return false
                currentFragment = favoriteFragment
            }
        }

        currentFragment?.let {
            replaceFragment(R.id.fragment, it)
        }

        return true
    }


    private fun replaceFragment(id: Int, fragment: Fragment) {

        if (supportFragmentManager.findFragmentById(id) == null) {
            supportFragmentManager.beginTransaction().apply {
                add(id, fragment)
                commit()
            }
        } else {
            supportFragmentManager.beginTransaction().apply {
                replace(id, fragment)
                addToBackStack(null)
                commit()
            }
        }

    }


}

