package com.abhi.inc.orufy_interview_assignment

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.text.SimpleDateFormat
import java.util.*
import androidx.core.content.edit


data class URLHistory(
    val url: String,
    val timestamp: String
)


class URLHistoryManager(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("url_history", Context.MODE_PRIVATE)
    private val gson = Gson()

    companion object {
        private const val KEY_HISTORY = "history_list"
    }

    fun saveURL(url: String) {
        val history = getHistory().toMutableList()
        val timestamp = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())

        history.add(0, URLHistory(url, timestamp))

        if (history.size > 50) {
            history.removeAt(history.size - 1)
        }

        val json = gson.toJson(history)
        sharedPreferences.edit { putString(KEY_HISTORY, json) }
    }

    fun getHistory(): List<URLHistory> {
        val json = sharedPreferences.getString(KEY_HISTORY, null) ?: return emptyList()
        val type = object : TypeToken<List<URLHistory>>() {}.type
        return gson.fromJson(json, type)
    }


    fun clearHistory() {
        sharedPreferences.edit { remove(KEY_HISTORY) }
    }
}