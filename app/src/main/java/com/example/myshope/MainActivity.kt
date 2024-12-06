package com.example.myshope

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.myshope.AllFragment.CardFragment
import com.example.myshope.AllFragment.HomeFragment
import com.example.myshope.AllFragment.SearchFragment
import com.example.myshope.AllFragment.SettingFragment
import com.example.myshope.AllFragment.WishlistFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    lateinit var buttonNavigationView: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonNavigationView = findViewById(R.id.bottom_navigation) as BottomNavigationView
        loadFragment(HomeFragment())
        buttonNavigationView.setOnItemSelectedListener { item ->

            when (item.itemId) {
                R.id.menu_home -> {
                    loadFragment(HomeFragment())
                    return@setOnItemSelectedListener true
                }
                R.id.menu_wishlist -> {
                    loadFragment(WishlistFragment())
                    return@setOnItemSelectedListener true
                }
                R.id.menu_card -> {
                    loadFragment(CardFragment())
                    return@setOnItemSelectedListener true
                }
                R.id.menu_search -> {
                    loadFragment(SearchFragment())
                    return@setOnItemSelectedListener true
                }
                R.id.menu_settings -> {
                    loadFragment(SettingFragment())
                    return@setOnItemSelectedListener true
                }

                else -> {
                    return@setOnItemSelectedListener false
                }
            }
        }
    }

    private fun loadFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container,fragment)
        transaction.commit()
    }

}