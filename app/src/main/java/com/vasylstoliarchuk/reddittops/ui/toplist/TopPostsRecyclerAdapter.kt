package com.vasylstoliarchuk.reddittops.ui.toplist

import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.vasylstoliarchuk.reddittops.R
import com.vasylstoliarchuk.reddittops.ui.common.inflate
import com.vasylstoliarchuk.reddittops.ui.toplist.model.TopPostsItem
import com.vasylstoliarchuk.reddittops.ui.toplist.model.TopPostsItemDiffUtil
import kotlinx.android.synthetic.main.top_posts_item.view.*

class TopPostsRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        private const val VIEW_TYPE_PROGRESS = 0
        private const val VIEW_TYPE_POST = 1
        private const val VIEW_TYPE_ERROR = 2
    }

    private val items = mutableListOf<TopPostsItem?>()

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_PROGRESS) {
            ProgressViewHolder(parent.inflate(R.layout.top_posts_progress_item))
        } else {
            PostViewHolder(parent.inflate(R.layout.top_posts_item))
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (items[position] == null) {
            VIEW_TYPE_PROGRESS
        } else {
            VIEW_TYPE_POST
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val post = items[position] ?: return

        if (holder is PostViewHolder) {
            with(holder.itemView) {
                if (post.thumbnailWidth != null && post.thumbnailWidth != 0 && post.thumbnailHeight != null && post.thumbnailHeight != 0) {
                    ivThumbnail.updateLayoutParams<ConstraintLayout.LayoutParams> {
                        dimensionRatio = "${post.thumbnailWidth.toFloat() / post.thumbnailHeight}"
                    }
                    ivThumbnail.visibility = View.VISIBLE
                    Picasso.get()
                        .load(post.thumbnail)
                        .noFade()
                        .into(ivThumbnail)
                } else {
                    ivThumbnail.visibility = View.GONE
                }
                tvHeader.text = resources.getString(R.string.top_posts_item_header_placeholder, post.author, post.createdUtc)
                tvTitle.text = post.title
            }
        }
    }

    override fun getItemId(position: Int): Long {
        return items[position]?.itemId ?: 0L
    }

    override fun getItemCount(): Int = items.size

    private class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    private class ProgressViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    fun setProgressVisible(visible: Boolean) {
        if (visible) {
            addNullItem()
        } else {
            removeNullItem()
        }
    }

    private fun addNullItem() {
        items.add(null)
        notifyItemInserted(items.size - 1)
    }

    private fun removeNullItem() {
        items.removeAt(items.size - 1)
        notifyItemRemoved(items.size)
    }

    fun showError(message: String?) {

    }
}