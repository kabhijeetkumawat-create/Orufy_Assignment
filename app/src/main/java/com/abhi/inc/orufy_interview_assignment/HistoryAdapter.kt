package com.abhi.inc.orufy_interview_assignment

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HistoryAdapter(
    private var historyList: List<URLHistory>,
    private val onItemClick: (URLHistory) -> Unit
) : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    inner class HistoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val urlText: TextView = view.findViewById(R.id.urlText)
        val timestampText: TextView = view.findViewById(R.id.timestampText)

        fun bind(history: URLHistory) {
            urlText.text = history.url
            timestampText.text = history.timestamp

            itemView.setOnClickListener {
                onItemClick(history)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_history, parent, false)
        return HistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(historyList[position])
    }

    override fun getItemCount(): Int = historyList.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newList: List<URLHistory>) {
        historyList = newList
        notifyDataSetChanged()
    }
}