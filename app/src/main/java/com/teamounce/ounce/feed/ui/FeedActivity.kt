package com.teamounce.ounce.feed.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.chip.Chip
import com.teamounce.ounce.R
import com.teamounce.ounce.base.BindingActivity
import com.teamounce.ounce.databinding.ActivityFeedBinding
import com.teamounce.ounce.feed.viewmodel.FeedActivityViewModel
import com.teamounce.ounce.util.TransparentStatusBarObject
import com.teamounce.ounce.util.dpFloat
import kotlinx.android.synthetic.main.item_feed_filter_bottom_sheet.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class FeedActivity : BindingActivity<ActivityFeedBinding>(R.layout.activity_feed), CoroutineScope {

    private lateinit var job: Job
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private val mViewModel: FeedActivityViewModel by viewModels()
    private val feedBottomSheet: BottomSheetDialog by lazy {
        BottomSheetDialog(binding.root.context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        job = Job()

        TransparentStatusBarObject.setStatusBar(this)
        binding.apply {
            lifecycleOwner = this@FeedActivity
            viewModel = mViewModel
        }

        setToolBar()
        setBottomSheet()
        setClickListener()
    }

    private fun setClickListener() {
        binding.feedFilterImg.setOnClickListener {
            feedBottomSheet.show()
        }
    }

    private fun setBottomSheet() {
        feedBottomSheet.setContentView(R.layout.item_feed_filter_bottom_sheet)

        launch {
            runCatching {
                setChipItem()
            }.onSuccess {
                Log.e("FeedActivity", "Add Chip Complete")
            }.onFailure { e ->
                Log.e("FeedActivity", "catch exception")
                e.printStackTrace()
            }
        }


        // 확인 버튼
        feedBottomSheet.feed_bottom_sheet_ok_txt.setOnClickListener {
            // add Event
            feedBottomSheet.dismiss()
        }
    }

    // 바텀시트 chip 아이템 생성
    private suspend fun setChipItem() {
        // 태그 chip 추가
        for (i in mViewModel.tagFilterSample) {
            val chip = Chip(binding.root.context)
            chip.apply {
                layoutDirection = View.LAYOUT_DIRECTION_LOCALE
                text = i
                textAlignment = View.TEXT_ALIGNMENT_CENTER
                chipStrokeWidth = 1.dpFloat
                setChipStrokeColorResource(R.color.feed_bottom_sheet_select_color)
                setTextAppearanceResource(R.style.Feed_BottomSheet_Text)
                setChipBackgroundColorResource(R.color.white)
            }
            feedBottomSheet.feed_bottom_sheet_tag_chip_group.addView(chip)
        }
        // 브랜드 chip 추가
        for (i in mViewModel.brandFilterSample) {
            val chip = Chip(binding.root.context)
            chip.apply {
                layoutDirection = View.LAYOUT_DIRECTION_LOCALE
                text = i
                textAlignment = View.TEXT_ALIGNMENT_CENTER
                chipStrokeWidth = 1.dpFloat
                setChipStrokeColorResource(R.color.feed_bottom_sheet_select_color)
                setTextAppearanceResource(R.style.Feed_BottomSheet_Text)
                setChipBackgroundColorResource(R.color.white)
            }
            feedBottomSheet.feed_bottom_sheet_brand_chip_group.addView(chip)
        }
    }

    private fun setToolBar() {
        binding.feedToolBar.setNavigationOnClickListener {
            finish()
        }
    }
}