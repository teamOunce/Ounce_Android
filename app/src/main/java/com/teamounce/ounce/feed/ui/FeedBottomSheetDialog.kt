package com.teamounce.ounce.feed.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.teamounce.ounce.R
import com.teamounce.ounce.databinding.ItemFeedFilterBottomSheetBinding
import com.teamounce.ounce.feed.viewmodel.FeedActivityViewModel
import com.teamounce.ounce.util.dpFloat
import kotlinx.android.synthetic.main.item_feed_filter_bottom_sheet.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class FeedBottomSheetDialog(private val viewModel : FeedActivityViewModel) : BottomSheetDialogFragment(),CoroutineScope {

    private lateinit var job : Job
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private var _binding : ItemFeedFilterBottomSheetBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        job = Job()
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ItemFeedFilterBottomSheetBinding.inflate(inflater,container,false)
        setChip()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.feedBottomSheetOkTxt.setOnClickListener {
            tagChipGroupChanged()
            brandChipGroupChanged()
            dismiss()
        }
    }

    private fun setChip() {
        launch {
            // 태그 chip 추가
            for (i in viewModel.tagFilterSample2) {
                val chip = makeChipItem(i)
                binding.feedBottomSheetTagChipGroup.addView(chip)
            }

            // 브랜드 chip 추가
            for (i in viewModel.brandFilterSample2) {
                val chip = makeChipItem(i)
                binding.feedBottomSheetBrandChipGroup.addView(chip)
            }
        }
    }

    private fun tagChipGroupChanged(){
        launch {
            runCatching {
                for(i in binding.feedBottomSheetTagChipGroup.children){
                    val chip = i as Chip
                    viewModel.tagFilterSample2[chip.text.toString()] = chip.isChecked
                }
            }.onSuccess {
                Log.e("FeedChip","Chip setting Success")
            }.onFailure { e ->
                Log.e("FeedChip","Tag Chip setting Fail")
                e.printStackTrace()
            }
        }

    }

    private fun brandChipGroupChanged(){
        launch {
            runCatching {
                for(i in binding.feedBottomSheetBrandChipGroup.children){
                    val chip = i as Chip
                    viewModel.tagFilterSample2[chip.text.toString()] = chip.isChecked
                }
            }.onSuccess {
                Log.e("FeedChip","Chip setting Success")
            }.onFailure { e ->
                Log.e("FeedChip","Brand Chip setting Fail")
                e.printStackTrace()
            }
        }

    }

    private fun makeChipItem(i : MutableMap.MutableEntry<String,Boolean>) : Chip {
        val chip = Chip(binding.root.context)
        chip.apply {
            layoutDirection = View.LAYOUT_DIRECTION_LOCALE
            text = i.key
            textAlignment = View.TEXT_ALIGNMENT_CENTER
            chipStrokeWidth = 1.dpFloat
            isCheckable = true
            isChecked = i.value
            isCheckedIconVisible = false
            setChipStrokeColorResource(R.color.feed_bottom_sheet_select_color)
            setTextAppearanceResource(R.style.Feed_BottomSheet_Text)
            setChipBackgroundColorResource(R.color.white)
            setRippleColorResource(R.color.orange2)
        }

        return chip
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}