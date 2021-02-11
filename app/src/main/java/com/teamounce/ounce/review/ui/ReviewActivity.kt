package com.teamounce.ounce.review.ui

import android.os.Bundle
import android.widget.CompoundButton
import android.widget.Toast
import androidx.activity.viewModels
import com.google.android.material.chip.Chip
import com.teamounce.ounce.R
import com.teamounce.ounce.base.BindingActivity
import com.teamounce.ounce.databinding.ActivityReviewBinding
import com.teamounce.ounce.review.adapter.CatFoodSliderAdapter
import com.teamounce.ounce.review.model.ResponseSearch
import com.teamounce.ounce.review.viewmodel.ReviewViewModel
import com.teamounce.ounce.util.ChipFactory
import com.teamounce.ounce.util.StatusBarUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReviewActivity : BindingActivity<ActivityReviewBinding>(R.layout.activity_review) {
    private val reviewViewModel: ReviewViewModel by viewModels()
    private lateinit var imageSliderAdapter: CatFoodSliderAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StatusBarUtil.setStatusBar(this)
        setUIListener()
        setObserver()
        reviewViewModel.getTags()
        imageSliderAdapter = CatFoodSliderAdapter()
        val initCatFoodData = intent.getParcelableExtra<ResponseSearch.Data>("catFood")
            ?: throw IllegalArgumentException("왜 없어 에반데")
        initSetting(initCatFoodData)

    }

    private fun initSetting(catFood: ResponseSearch.Data) {
        imageSliderAdapter.replaceList(listOf(catFood.productImg))
        binding.apply {
            txtRecordBrand.text = catFood.manufacturer
            txtRecordFood.text = catFood.productName
            vpRecordSlider.adapter = imageSliderAdapter
            dotsIndicator.setViewPager2(binding.vpRecordSlider)
        }
    }

    private fun setUIListener() {
        binding.imgReviewBack.setOnClickListener { finish() }
        binding.ratingRecordPreference.setOnRatingChangeListener {
            reviewViewModel.preference = it
            binding.btnSubmit.isEnabled = true
        }
    }

    private fun setObserver() {
        reviewViewModel.tagList.observe(this) { it ->
            it.asSequence()
                .map { "#${it.tag}" }
                .map { it.toChip() }
                .map { it.also { it.setOnCheckedChangeListener(chipCheckedChangeListener()) } }
                .forEach { binding.chipgroupRecordTag.addView(it) }
        }
        reviewViewModel.warningMessage.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }

    private fun String.toChip(): Chip =
        ChipFactory.create(layoutInflater).also { it.text = this }

    private fun chipCheckedChangeListener(): CompoundButton.OnCheckedChangeListener {
        return CompoundButton.OnCheckedChangeListener { compoundButton, checked ->
            if (checked) {
                if (reviewViewModel.isTagsFull) {
                    compoundButton.isChecked = false
                } else {
                    reviewViewModel.addTag(compoundButton.text.toString())
                }
            } else {
                reviewViewModel.deleteTag(compoundButton.text.toString())
            }
        }
    }
}