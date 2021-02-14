package com.teamounce.ounce.util

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.teamounce.ounce.feed.adapter.FeedListAdapter
import com.teamounce.ounce.feed.model.ResponseFeedReviewData

// FeedActivity RecyclerView 적용
@BindingAdapter("app:setFeedList")
fun RecyclerView.setFeedList(data : List<ResponseFeedReviewData.Data>){
    val rcvAdapter = FeedListAdapter()

    rcvAdapter.feedList = data
    this.apply {
        layoutManager = LinearLayoutManager(this.context)
        adapter = rcvAdapter
    }
    rcvAdapter.notifyDataSetChanged()
}