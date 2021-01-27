package com.teamounce.ounce.feed.ui

import android.os.Bundle
import androidx.activity.viewModels
import com.teamounce.ounce.R
import com.teamounce.ounce.base.BindingActivity
import com.teamounce.ounce.databinding.ActivityFoodDetailBinding
import com.teamounce.ounce.feed.adapter.FoodImageSliderAdapter
import com.teamounce.ounce.feed.adapter.TagAdapter
import com.teamounce.ounce.feed.viewmodel.FeedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FoodDetailActivity :
    BindingActivity<ActivityFoodDetailBinding>(R.layout.activity_food_detail) {

    private val feedViewModel: FeedViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.lifecycleOwner = this
        //binding.viewModel = feedViewModel
        feedViewModel.fetchReviewData()

        val tagAdapter = TagAdapter()
        binding.rvDetailTag.adapter = tagAdapter

        val foodImageSliderAdapter = FoodImageSliderAdapter()
        binding.vpDetailImageSlider.adapter = foodImageSliderAdapter

        feedViewModel.foodReview.observe(this) {
            binding.review = it.catFoodReview

            val tagList = listOf(
                it.catFoodReview.tag1,
                it.catFoodReview.tag2,
                it.catFoodReview.tag3
            ).filter { tag -> tag != "" }
            tagAdapter.replaceList(tagList)

            val imageUrlList = listOf(
                it.catFoodReview.productImg,
                it.catFoodReview.myImg
            ).filter { url -> url != "" }
            foodImageSliderAdapter.replaceList(imageUrlList)
        }
    }
}