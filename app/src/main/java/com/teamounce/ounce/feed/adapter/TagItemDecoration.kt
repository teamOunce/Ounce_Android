package com.teamounce.ounce.feed.adapter

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.teamounce.ounce.util.dp

class TagItemDecoration : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.set(0.dp, 14.dp, 0.dp, 14.dp)
    }
}