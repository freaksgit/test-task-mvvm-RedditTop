package com.vasylstoliarchuk.reddittops.ui.common.recyclerview

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SpacingBetweenItemDecoration(private val space: Int = 0) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view)

        when (val manager = parent.layoutManager) {
            is LinearLayoutManager -> {
                if (position == 0) return
                when (manager.orientation) {
                    RecyclerView.HORIZONTAL -> {
                        outRect.left = space
                    }
                    RecyclerView.VERTICAL -> {
                        outRect.top = space
                    }
                }
            }
        }
    }
}