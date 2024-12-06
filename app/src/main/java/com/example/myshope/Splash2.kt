package com.example.myshope

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator

class Splash2 : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash2)

        val viewpager=findViewById<ViewPager2>(R.id.viewpager)
        val next=findViewById<TextView>(R.id.nextSlide)
        val tabLayout = findViewById<com.google.android.material.tabs.TabLayout>(R.id.tabLayout)


        val images = listOf(
            R.layout.slide_activity,
            R.layout.slidelayout2,
            R.layout.slidelayout3
        )

        val adapter = ImageSliderAdapter(images)
        viewpager.adapter = adapter

        TabLayoutMediator(tabLayout, viewpager) { _, _ -> }.attach()

        next.setOnClickListener {
            val nextItem = viewpager.currentItem + 1
            if (nextItem < images.size) {
                viewpager.setCurrentItem(nextItem, true) // Enable smooth slide animation
            } else {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

    }
}