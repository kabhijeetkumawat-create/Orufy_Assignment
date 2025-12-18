package com.abhi.inc.orufy_interview_assignment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator

class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toolbar: MaterialToolbar
    private lateinit var imageSlider: ViewPager2
    private lateinit var dotsIndicator: DotsIndicator

    private val sliderHandler = Handler(Looper.getMainLooper())
    private var currentPage = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        drawerLayout = findViewById(R.id.main)
        toolbar = findViewById(R.id.topAppBar)
        imageSlider = findViewById(R.id.imageSlider)
        dotsIndicator = findViewById(R.id.dotsIndicator)
        val navView: NavigationView = findViewById(R.id.navView)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        toolbar.setNavigationOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        // Image Slider setup
        setupImageSlider()

        navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.History -> {
                    val intent = Intent(this, History::class.java)
                    startActivity(intent)
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.CV -> {
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                else -> false
            }
        }

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START)
                } else {
                    isEnabled = false
                    onBackPressedDispatcher.onBackPressed()
                }
            }
        })
    }

    private fun setupImageSlider() {
        //  Added images
        val images = listOf(
            R.drawable.electronics_banner,
            R.drawable.facewash_banner,
            R.drawable.food_banner
        )

        val adapter = ImageSliderAdapter(images)
        imageSlider.adapter = adapter

        //  dots indicator
        dotsIndicator.attachTo(imageSlider)

        val sliderRunnable = object : Runnable {
            override fun run() {
                if (currentPage == images.size) {
                    currentPage = 0
                }
                imageSlider.setCurrentItem(currentPage++, true)
                sliderHandler.postDelayed(this, 3000) // scroll for every 3 seconds
            }
        }

        sliderHandler.postDelayed(sliderRunnable, 3000)

    }

    override fun onDestroy() {
        super.onDestroy()
        sliderHandler.removeCallbacksAndMessages(null)
    }
}