package com.vasylstoliarchuk.reddittops.ui.toplist.adapter

import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.vasylstoliarchuk.reddittops.R
import com.vasylstoliarchuk.reddittops.ui.common.inflate
import kotlinx.android.synthetic.main.top_posts_error_item.view.*

class ErrorRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var item: ErrorItem? = null
        set(value) {
            if (field != value) {
                field = value
                notifyDataSetChanged()
            }
        }

    var onErrorClickListener: (() -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ErrorViewHolder(parent.inflate(R.layout.top_posts_error_item)).apply {
            itemView.btnRetry.setOnClickListener { onErrorClickListener?.invoke() }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = item ?: return
        with(holder.itemView) {
            tvMessage.isVisible = !item.message.isNullOrBlank()
            tvMessage.text = item.message
        }
    }

    override fun getItemCount(): Int = if (item == null) 0 else 1

    fun showError(errorItem: ErrorItem) {
        this.item = errorItem
    }

    fun hideError() {
        item = null
    }

    data class ErrorItem(val message: String?)
    class ErrorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}