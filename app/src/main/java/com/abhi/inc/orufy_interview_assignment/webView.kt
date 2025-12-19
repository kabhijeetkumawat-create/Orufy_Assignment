package com.abhi.inc.orufy_interview_assignment

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar

class WebViewActivity : AppCompatActivity() {

    private lateinit var webView: WebView
    private lateinit var progressBar: ProgressBar
    private lateinit var toolbar: MaterialToolbar
    private lateinit var closeButton: ImageButton
    private lateinit var urlDisplay: TextView
    private var initialUrl: String = ""

    companion object {
        const val EXTRA_URL = "URL"
        const val EXTRA_RETAIN_URL = "RETAIN_URL"
        const val EXTRA_CLEAR_URL = "CLEAR_URL"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        initializeViews()
        setupToolbar()
        setupWebView()
        setupBackPress()

        initialUrl = intent.getStringExtra(EXTRA_URL) ?: ""
        loadURL(initialUrl)
    }

    private fun initializeViews() {
        webView = findViewById(R.id.webView)
        progressBar = findViewById(R.id.progressBar)
        toolbar = findViewById(R.id.toolbar)
        closeButton = findViewById(R.id.closeButton)
        urlDisplay = findViewById(R.id.urlDisplay)
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Back button - returns to Home Screen with URL there
        toolbar.setNavigationOnClickListener {
            navigateBackWithRetainedURL()
        }

        // Close button - returns to Home Screen with URL cleared
        closeButton.setOnClickListener {
            navigateBackWithClearedURL()
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebView() {
        webView.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
            loadWithOverviewMode = true
            useWideViewPort = true
            builtInZoomControls = true
            displayZoomControls = false
            setSupportZoom(true)
        }

        webView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                progressBar.visibility = View.VISIBLE

                urlDisplay.text = url ?: ""
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                progressBar.visibility = View.GONE

                toolbar.title = view?.title ?: "WebView"
                urlDisplay.text = url ?: ""
            }
        }

        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                progressBar.progress = newProgress
            }
        }
    }

    private fun setupBackPress() {
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (webView.canGoBack()) {
                    webView.goBack()
                } else {
                    navigateBackWithRetainedURL()
                }
            }
        })
    }

    private fun loadURL(url: String) {
        urlDisplay.text = url
        webView.loadUrl(url)
    }


    private fun navigateBackWithRetainedURL() {
        val currentUrl = webView.url ?: initialUrl
        val resultIntent = Intent().apply {
            putExtra(EXTRA_RETAIN_URL, currentUrl)
        }
        setResult(RESULT_OK, resultIntent)
        finish()
    }

    private fun navigateBackWithClearedURL() {
        val resultIntent = Intent().apply {
            putExtra(EXTRA_CLEAR_URL, true)
        }
        setResult(RESULT_OK, resultIntent)
        finish()
    }
}