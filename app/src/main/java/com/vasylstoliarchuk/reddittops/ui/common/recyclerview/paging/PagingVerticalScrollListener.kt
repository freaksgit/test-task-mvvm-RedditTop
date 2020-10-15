package com.vasylstoliarchuk.reddittops.ui.common.recyclerview.paging

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PagingVerticalScrollListener(private val prefetchDistance: Int = 15) : VerticalScrollListener() {
    private val listeners: MutableSet<OnFetchNextPageListener> = HashSet()

    fun addOnPagingDataListener(onPagingDataListener: OnFetchNextPageListener) {
        listeners.add(onPagingDataListener)
    }

    fun removeOnPagingDataListener(onPagingDataListener: OnFetchNextPageListener) {
        listeners.remove(onPagingDataListener)
    }

    override fun onScrolledDown(recyclerView: RecyclerView) {
        val layoutManager = recyclerView.layoutManager ?: return
        val adapter = recyclerView.adapter ?: return
        if (layoutManager is LinearLayoutManager) {
            val lastVisible = layoutManager.findFirstVisibleItemPosition()
            val size = adapter.itemCount
            if (size - lastVisible <= prefetchDistance) {
                notifyListeners()
            }
        }
    }

    private fun notifyListeners() {
        for (listener in listeners) {
            listener.onFetchNextPage()
        }
    }
}