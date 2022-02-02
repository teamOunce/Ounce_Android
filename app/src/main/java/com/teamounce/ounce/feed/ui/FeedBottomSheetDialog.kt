package com.teamounce.ounce.feed.ui

import android.app.Dialog
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
import com.teamounce.ounce.util.ChipFactory
import com.teamounce.ounce.util.dpFloat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class FeedBottomSheetDialog(private val viewModel: FeedActivityViewModel) :
    BottomSheetDialogFragment(), CoroutineScope {

    private lateinit var job: Job
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private var _binding: ItemFeedFilterBottomSheetBinding? = null
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
        _binding = ItemFeedFilterBottomSheetBinding.inflate(inflater, container, false)

        setObserve()
        return binding.root
    }

    private fun setObserve() {
        viewModel.filterSet.observe(viewLifecycleOwner) {
            if (it) {
                setChip()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.feedBottomSheetResetTxt.setOnClickListener {
            binding.feedBottomSheetFilterDry.isChecked = false
            binding.feedBottomSheetFilterWet.isChecked = false
            resetAllChip()
        }

        binding.feedBottomSheetOkTxt.setOnClickListener {
            setChipGroupChanged()
            dismiss()
        }

        binding.feedBottomSheetFilterDry.isChecked = viewModel.dryCheck
        binding.feedBottomSheetFilterWet.isChecked = viewModel.wetCheck
    }

    private fun resetAllChip() {
        launch {
            for (i in binding.feedBottomSheetTagChipGroup.children) {
                val chip = i as Chip
                chip.isChecked = false
            }

            for (i in binding.feedBottomSheetBrandChipGroup.children) {
                val chip = i as Chip
                chip.isChecked = false
            }
        }
    }

    private fun setChip() {
        launch {
            // 태그 chip 추가
            for (i in viewModel.tagFilterList) {
                val chip = makeChipItem(i)
                binding.feedBottomSheetTagChipGroup.addView(chip)
            }

            // 브랜드 chip 추가
            for (i in viewModel.brandFilterList) {
                val chip = makeChipItem(i)
                binding.feedBottomSheetBrandChipGroup.addView(chip)
            }
        }
    }

    // tag 필터 적용 여부 결정
    private fun setChipGroupChanged() {
        launch {
            runCatching {
                for (i in binding.feedBottomSheetTagChipGroup.children) {
                    val chip = i as Chip
                    viewModel.tagFilterList[chip.text.toString()] = chip.isChecked
                }

                for (i in binding.feedBottomSheetBrandChipGroup.children) {
                    val chip = i as Chip
                    viewModel.brandFilterList[chip.text.toString()] = chip.isChecked
                }
                // 건식,습식 체크 여부
                viewModel.dryCheck = binding.feedBottomSheetFilterDry.isChecked
                viewModel.wetCheck = binding.feedBottomSheetFilterWet.isChecked

                // 필터 체크 적용하기
                viewModel.applicationFilter()
            }.onSuccess {
                Log.e("FeedChip", "Chip setting Success")
            }.onFailure { e ->
                Log.e("FeedChip", "Tag Chip setting Fail")
                e.printStackTrace()
            }
        }

    }

    private fun makeChipItem(i: MutableMap.MutableEntry<String, Boolean>): Chip {
        return ChipFactory.create(layoutInflater).apply {
            text = i.key
            setRippleColorResource(R.color.orange2)
        }
    }

    override fun onDestroy() {
        job.cancel()
        Log.e("Destroy", "call Destroy")
        super.onDestroy()
    }
}