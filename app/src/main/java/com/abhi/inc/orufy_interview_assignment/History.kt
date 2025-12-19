package com.abhi.inc.orufy_interview_assignment

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar

class History : AppCompatActivity() {

    private lateinit var toolbar: MaterialToolbar
    private lateinit var historyRecyclerView: RecyclerView
    private lateinit var emptyState: LinearLayout
    private lateinit var actionButtons: LinearLayout
    private lateinit var clearButton: MaterialButton
    private lateinit var uploadButton: MaterialButton

    private lateinit var urlHistoryManager: URLHistoryManager
    private lateinit var historyAdapter: HistoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        toolbar = findViewById(R.id.toolbar)
        historyRecyclerView = findViewById(R.id.historyRecyclerView)
        emptyState = findViewById(R.id.emptyState)
        actionButtons = findViewById(R.id.actionButtons)
        clearButton = findViewById(R.id.clearButton)
        uploadButton = findViewById(R.id.uploadButton)

        urlHistoryManager = URLHistoryManager(this)

        setupToolbar()
        setupRecyclerView()
        setupButtons()
        loadHistory()
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { finish() }
    }

    private fun setupRecyclerView() {
        historyAdapter = HistoryAdapter(emptyList()) { history ->
            val intent = Intent(this, WebViewActivity::class.java)
            intent.putExtra(WebViewActivity.EXTRA_URL, history.url)
            startActivity(intent)
        }

        historyRecyclerView.layoutManager = LinearLayoutManager(this)
        historyRecyclerView.adapter = historyAdapter
    }

    private fun setupButtons() {
        clearButton.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Clear History")
                .setMessage("Are you sure you want to delete all browsing history?")
                .setPositiveButton("Clear") { dialog, _ ->
                    urlHistoryManager.clearHistory()
                    loadHistory()
                    Snackbar.make(
                        findViewById(android.R.id.content),
                        "History cleared successfully",
                        Snackbar.LENGTH_SHORT
                    ).show()
                    dialog.dismiss()
                }
                .setNegativeButton("Cancel") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }

        uploadButton.setOnClickListener {
            val historyList = urlHistoryManager.getHistory()
            if (historyList.isEmpty()) {
                Toast.makeText(this, "No history to upload", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Upload feature - integrate with Beeceptor", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loadHistory() {
        val historyList = urlHistoryManager.getHistory()

        if (historyList.isEmpty()) {
            emptyState.visibility = View.VISIBLE
            historyRecyclerView.visibility = View.GONE
            actionButtons.visibility = View.GONE
        } else {
            emptyState.visibility = View.GONE
            historyRecyclerView.visibility = View.VISIBLE
            actionButtons.visibility = View.VISIBLE
        }

        historyAdapter.updateData(historyList)
    }

    override fun onResume() {
        super.onResume()
        loadHistory()
    }
}