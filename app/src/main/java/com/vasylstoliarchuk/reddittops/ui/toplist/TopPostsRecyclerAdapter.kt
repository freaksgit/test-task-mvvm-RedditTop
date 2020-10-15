package com.vasylstoliarchuk.reddittops.ui.toplist

import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.vasylstoliarchuk.reddittops.R
import com.vasylstoliarchuk.reddittops.ui.common.inflate
import com.vasylstoliarchuk.reddittops.ui.toplist.adapter.TopPostsItem
import kotlinx.android.synthetic.main.top_posts_item.view.*

class TopPostsRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val items = mutableListOf<TopPostsItem>()

    fun swapItems(newItems: List<TopPostsItem>) {
        items.clear()
        items.addAll(newItems)
        //TODO: use TopPostsItemDiffUtil after fixes of the ConcatAdapter issues with DiffResult dispatching
        notifyDataSetChanged()
    }

    fun appendItems(newItems: List<TopPostsItem>) {
        val oldItemCount = itemCount
        items.addAll(newItems)
        notifyItemRangeInserted(oldItemCount, newItems.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PostViewHolder(parent.inflate(R.layout.top_posts_item))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        with(holder.itemView) {
            if (item.thumbnailWidth != null && item.thumbnailWidth != 0 && item.thumbnailHeight != null && item.thumbnailHeight != 0) {
                ivThumbnail.updateLayoutParams<ConstraintLayout.LayoutParams> {
                    dimensionRatio = "${item.thumbnailWidth.toFloat() / item.thumbnailHeight}"
                }
                ivThumbnail.visibility = View.VISIBLE
                Picasso.get()
                    .load(item.thumbnail)
                    .into(ivThumbnail)
            } else {
                ivThumbnail.visibility = View.GONE
            }
            tvHeader.text = resources.getString(R.string.top_posts_item_header_placeholder, item.author, item.createdUtc)
            tvTitle.text = item.title
            tvCommentsNum.text = resources.getString(R.string.top_posts_item_comments_number_placeholder, item.numComments)
        }
    }

    override fun getItemCount(): Int = items.size

    private class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}