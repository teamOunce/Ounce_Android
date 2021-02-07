package com.teamounce.ounce.feed.ui

import android.os.Bundle
import androidx.activity.viewModels
import com.teamounce.ounce.R
import com.teamounce.ounce.base.BindingActivity
import com.teamounce.ounce.databinding.ActivityFeedBinding
import com.teamounce.ounce.feed.viewmodel.FeedActivityViewModel
import com.teamounce.ounce.util.TransparentStatusBarObject

class FeedActivity :BindingActivity<ActivityFeedBinding>(R.layout.activity_feed) {

    private val mViewModel : FeedActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TransparentStatusBarObject.setStatusBar(this)
        binding.apply {
            lifecycleOwner = this@FeedActivity
            viewModel = mViewModel
        }

        setToolBar()
    }

    private fun setToolBar() {
        binding.feedToolBar.setNavigationOnClickListener {
            finish()
        }
    }
}