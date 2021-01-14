package com.teamounce.ounce.util

import android.graphics.Rect
import androidx.recyclerview.widget.RecyclerView

class VerticalItemDecoration(private val divHeight: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, itemPosition: Int, parent: RecyclerView) {
        outRect.top = divHeight
        outRect.bottom = divHeight
    }
}