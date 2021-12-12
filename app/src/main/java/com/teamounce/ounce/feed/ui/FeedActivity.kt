package com.teamounce.ounce.feed.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.teamounce.ounce.R
import com.teamounce.ounce.base.BindingActivity
import com.teamounce.ounce.data.enum.EnumFeedSort
import com.teamounce.ounce.data.local.singleton.OunceLocalRepository
import com.teamounce.ounce.databinding.ActivityFeedBinding
import com.teamounce.ounce.feed.adapter.FeedSortAdapter
import com.teamounce.ounce.feed.viewmodel.FeedActivityViewModel
import com.teamounce.ounce.util.StatusBarUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.item_feed_filter_bottom_sheet.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

@AndroidEntryPoint
class FeedActivity : BindingActivity<ActivityFeedBinding>(R.layout.activity_feed), CoroutineScope {

    private val BOTTOM_SHEET_TAG = "bottomSheetTag"
    private lateinit var job: Job
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private val mViewModel: FeedActivityViewModel by viewModels()
    private val bottomSheet: FeedBottomSheetDialog by lazy {
        FeedBottomSheetDialog(mViewModel)
    }

    private val feedBottomSheet: FeedSortBottomSheet by lazy {
        FeedSortBottomSheet()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        job = Job()

        StatusBarUtil.setStatusBar(this)
        binding.apply {
            lifecycleOwner = this@FeedActivity
            viewModel = mViewModel
        }

        setObserve()

        mViewModel.getBottomSheetFiltering()
        launch {
            mViewModel.callFeedList()
        }

        initSortBottomSheet()

        getCatNameAndSetTitle()
        setToolBar()
        setClickListener()
        binding.feedHashTagImg.setOnClickListener {
            val intent = Intent(this, TagActivity::class.java)
            startActivity(intent)
        }
        binding.feedSwipeLayout.setColorSchemeColors(ContextCompat.getColor(this, R.color.orange))
        binding.feedSwipeLayout.setOnRefreshListener {
            val intent = Intent(this, FeedActivity::class.java)
            finish()
            overridePendingTransition(0, 0)
            startActivity(intent)

            binding.feedSwipeLayout.isRefreshing = false
        }
    }

    override fun onRestart() {
        super.onRestart()
        val intent = Intent(this, FeedActivity::class.java)
        finish()
        overridePendingTransition(0, 0)
        startActivity(intent)
    }

    private fun setObserve() {
        mViewModel.responseFeedList.observe(this, {
            if (it.isEmpty()) {
                binding.noFeedTxt.visibility = View.VISIBLE
            } else {
                binding.noFeedTxt.visibility = View.INVISIBLE
            }
        })
    }

    private fun initSortBottomSheet() {
        feedBottomSheet.run {
            setSortListener(object : FeedSortAdapter.ItemListener {
                override fun clickSortStatus(status: EnumFeedSort) {
                    mViewModel.changeSort(status)
                    dismiss()
                }
            })
        }
    }

    @SuppressLint("SetTextI18n")
    private fun getCatNameAndSetTitle() {
        binding.feedToolTitle.text = "${OunceLocalRepository.catName}의 기록"
    }

    private fun setClickListener() {
        binding.feedFilterImg.setOnClickListener {
            bottomSheet.isCancelable = false
            bottomSheet.show(supportFragmentManager, BOTTOM_SHEET_TAG)
        }

        binding.txtFeedSort.setOnClickListener {
            feedBottomSheet.showSortBottomSheet(this, mViewModel.feedSortEnum)
        }
    }

    private fun setToolBar() {
        binding.feedToolBar.setNavigationOnClickListener {
            finish()
        }
    }
}