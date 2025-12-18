package com.abhi.inc.orufy_interview_assignment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Patterns
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator

class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toolbar: MaterialToolbar
    private lateinit var imageSlider: ViewPager2
    private lateinit var dotsIndicator: DotsIndicator
    private lateinit var urlInput: TextInputEditText
    private lateinit var urlInputLayout: TextInputLayout
    private lateinit var openButton: MaterialButton
    private lateinit var urlHistoryManager: URLHistoryManager

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

        initializeViews()
        setupToolbar()
        setupImageSlider()
        setupURLInput()
        setupNavigation()
        setupBackPress()
    }

    private fun initializeViews() {
        drawerLayout = findViewById(R.id.main)
        toolbar = findViewById(R.id.topAppBar)
        imageSlider = findViewById(R.id.imageSlider)
        dotsIndicator = findViewById(R.id.dotsIndicator)
        urlInput = findViewById(R.id.urlInput)
        urlInputLayout = findViewById(R.id.urlInputLayout)
        openButton = findViewById(R.id.openButton)
        urlHistoryManager = URLHistoryManager(this)
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        toolbar.setNavigationOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }
    }

    private fun setupImageSlider() {
        val images = listOf(
            R.drawable.electronics_banner,
            R.drawable.facewash_banner,
            R.drawable.food_banner
        )

        val adapter = ImageSliderAdapter(images)
        imageSlider.adapter = adapter
        dotsIndicator.attachTo(imageSlider)

        val sliderRunnable = object : Runnable {
            override fun run() {
                if (currentPage == images.size) {
                    currentPage = 0
                }
                imageSlider.setCurrentItem(currentPage++, true)
                sliderHandler.postDelayed(this, 3000)
            }
        }

        sliderHandler.postDelayed(sliderRunnable, 3000)

        imageSlider.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                currentPage = position
            }
        })
    }

    private fun setupURLInput() {
        openButton.setOnClickListener {
            val urlText = urlInput.text.toString().trim()

            when {
                urlText.isEmpty() -> {
                    urlInputLayout.error = "Please enter a URL"
                    Snackbar.make(
                        findViewById(android.R.id.content),
                        "URL cannot be empty",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
                !isValidURL(urlText) -> {
                    urlInputLayout.error = "Please enter a valid URL"
                    Snackbar.make(
                        findViewById(android.R.id.content),
                        "Please enter a valid URL",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
                else -> {
                    urlInputLayout.error = null
                    val validURL = formatURL(urlText)

                    // Save to history
                    urlHistoryManager.saveURL(validURL)

                    // Navigate to WebView
                    val intent = Intent(this, WebViewActivity::class.java)
                    intent.putExtra("URL", validURL)
                    startActivity(intent)

                    // Clear input
                    urlInput.text?.clear()
                }
            }
        }

        // Clear error when user starts typing
        urlInput.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                urlInputLayout.error = null
            }
        }

        // Handle IME action (when user presses "Go" on keyboard)
        urlInput.setOnEditorActionListener { _, _, _ ->
            openButton.performClick()
            true
        }
    }

    private fun isValidURL(url: String): Boolean {
        // Check if it's a valid URL pattern
        if (Patterns.WEB_URL.matcher(url).matches()) {
            return true
        }

        // Check if it's a domain without scheme (e.g., google.com)
        val domainPattern = "^([a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,}(/.*)?$".toRegex()
        return domainPattern.matches(url)
    }

    private fun formatURL(url: String): String {
        return when {
            url.startsWith("http://") || url.startsWith("https://") -> url
            else -> "https://$url"
        }
    }

    private fun setupNavigation() {
        val navView: NavigationView = findViewById(R.id.navView)

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
    }

    private fun setupBackPress() {
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

    override fun onDestroy() {
        super.onDestroy()
        sliderHandler.removeCallbacksAndMessages(null)
    }
}