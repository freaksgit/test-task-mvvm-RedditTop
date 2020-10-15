package com.vasylstoliarchuk.reddittops.ui.toplist.adapter

import androidx.recyclerview.widget.DiffUtil

class TopPostsItemDiffUtil(private val oldItems: List<TopPostsItem>, private val newItems: List<TopPostsItem>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldItems.size
    override fun getNewListSize(): Int = newItems.size
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = oldItems[oldItemPosition].id == newItems[newItemPosition].id
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = oldItems[oldItemPosition] == newItems[newItemPosition]
}