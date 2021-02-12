package com.teamounce.ounce.feed.ui

import android.os.Bundle
import androidx.activity.viewModels
import com.teamounce.ounce.R
import com.teamounce.ounce.base.BindingActivity
import com.teamounce.ounce.databinding.ActivityFeedBinding
import com.teamounce.ounce.feed.viewmodel.FeedActivityViewModel
import com.teamounce.ounce.util.StatusBarUtil
import kotlinx.android.synthetic.main.item_feed_filter_bottom_sheet.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

class FeedActivity : BindingActivity<ActivityFeedBinding>(R.layout.activity_feed), CoroutineScope {

    private val BOTTOM_SHEET_TAG = "bottomSheetTag"
    private lateinit var job: Job
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private val mViewModel: FeedActivityViewModel by viewModels()
    private val bottomSheet : FeedBottomSheetDialog by lazy {
        FeedBottomSheetDialog(mViewModel)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        job = Job()

        StatusBarUtil.setStatusBar(this)
        binding.apply {
            lifecycleOwner = this@FeedActivity
            viewModel = mViewModel
        }

        setToolBar()
        setClickListener()
    }

    private fun setClickListener() {
        binding.feedFilterImg.setOnClickListener {
            bottomSheet.show(supportFragmentManager,BOTTOM_SHEET_TAG)
        }
    }

    private fun setToolBar() {
        binding.feedToolBar.setNavigationOnClickListener {
            finish()
        }
    }
}