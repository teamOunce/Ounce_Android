package com.teamounce.ounce.feed.ui

import android.os.Bundle
import com.teamounce.ounce.R
import com.teamounce.ounce.base.BindingActivity
import com.teamounce.ounce.databinding.ActivityFeedBinding
import com.teamounce.ounce.util.TransparentStatusBarObject

class FeedActivity :BindingActivity<ActivityFeedBinding>(R.layout.activity_feed) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TransparentStatusBarObject.setStatusBar(this)
        binding.apply {
            lifecycleOwner = this@FeedActivity
        }

        setToolBar()
    }

    private fun setToolBar() {
        binding.feedToolBar.setNavigationOnClickListener {
            finish()
        }
    }
}