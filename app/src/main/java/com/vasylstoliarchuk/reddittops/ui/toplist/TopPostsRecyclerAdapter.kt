package com.vasylstoliarchuk.reddittops.ui.toplist

import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.vasylstoliarchuk.reddittops.R
import com.vasylstoliarchuk.reddittops.ui.common.inflate
import com.vasylstoliarchuk.reddittops.ui.toplist.model.TopPostsItem
import com.vasylstoliarchuk.reddittops.ui.toplist.model.TopPostsItemDiffUtil
import kotlinx.android.synthetic.main.top_posts_item.view.*
import kotlin.random.Random

class TopPostsRecyclerAdapter : RecyclerView.Adapter<TopPostsRecyclerAdapter.PostViewHolder>() {
    private val items = mutableListOf<TopPostsItem>()

    fun swapItems(newItems: List<TopPostsItem>) {
        items.clear()
        DiffUtil.calculateDiff(TopPostsItemDiffUtil(this.items, newItems))
            .dispatchUpdatesTo(this)
        items.addAll(newItems)
    }

    fun appendItems(newItems: List<TopPostsItem>) {
        val oldItemCount = itemCount
        items.addAll(newItems)
        notifyItemRangeInserted(oldItemCount, newItems.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder(parent.inflate(R.layout.top_posts_item))
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.itemView.tvTitle.setBackgroundColor(Color.argb(255, Random.nextInt(256), Random.nextInt(256), Random.nextInt(256)))
        holder.itemView.tvTitle.text = items[position].title // TODO: 10/14/2020 Actualize
    }

    override fun getItemCount(): Int = items.size

    class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}