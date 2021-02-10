package com.teamounce.ounce.review.adapter

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.teamounce.ounce.util.dp

class ResultItemDecoration : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.set(8.dp, 8.dp, 8.dp, 8.dp)
    }
}
