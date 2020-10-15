package com.vasylstoliarchuk.reddittops.ui.toplist.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vasylstoliarchuk.reddittops.R
import com.vasylstoliarchuk.reddittops.ui.common.inflate

class ProgressRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var item: ProgressItem? = null
        set(value) {
            if (field != value) {
                field = value
                notifyDataSetChanged()
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ProgressViewHolder(parent.inflate(R.layout.top_posts_progress_item))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {}

    override fun getItemCount(): Int = if (item == null) 0 else 1

    fun showProgress() {
        item = ProgressItem
    }

    fun hideProgress() {
        item = null
    }

    class ProgressViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    object ProgressItem
}