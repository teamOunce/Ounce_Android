package com.teamounce.ounce.feed.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.constraintlayout.widget.ConstraintLayout
import com.teamounce.ounce.R
import com.teamounce.ounce.base.BindingActivity
import com.teamounce.ounce.databinding.ActivityFoodDetailBinding
import com.teamounce.ounce.feed.adapter.FoodImageSliderAdapter
import com.teamounce.ounce.feed.adapter.TagAdapter
import com.teamounce.ounce.feed.viewmodel.FeedViewModel
import com.teamounce.ounce.util.StatusBarUtil
import com.teamounce.ounce.util.dp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FoodDetailActivity :
    BindingActivity<ActivityFoodDetailBinding>(R.layout.activity_food_detail) {

    private val feedViewModel: FeedViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StatusBarUtil.setStatusBar(this)
        binding.lifecycleOwner = this
        feedViewModel.fetchReviewData()

        val tagAdapter = TagAdapter()
        binding.rvDetailTag.adapter = tagAdapter

        val foodImageSliderAdapter = FoodImageSliderAdapter()
        binding.vpDetailImageSlider.adapter = foodImageSliderAdapter

        feedViewModel.foodReview.observe(this) {
            binding.review = it.catFoodReview
            binding.executePendingBindings()

            setDateTopMargin(it.catFoodReview.memo.isEmpty())
            with(it.catFoodReview.preference) {
                setCommentText(this)
                binding.ratingbarDetailRating.setStar(this.toFloat())
            }
            binding.ratingbarDetailRating.setmClickable(false)

            val tagList = listOf(
                it.catFoodReview.tag1,
                it.catFoodReview.tag2,
                it.catFoodReview.tag3
            ).filter { tag -> tag != "" }
            tagAdapter.replaceList(tagList)
            setReviewBoxTopMargin(tagList.isEmpty())

            val imageUrlList = listOf(
                it.catFoodReview.productImg,
                it.catFoodReview.myImg
            ).filter { url -> url != "" }
            foodImageSliderAdapter.replaceList(imageUrlList)
        }
    }

    private fun setReviewBoxTopMargin(isTagEmpty: Boolean) {
        val reviewBoxLayoutParams =
            binding.viewDetailRatingBackground.layoutParams as ConstraintLayout.LayoutParams
        when (isTagEmpty) {
            true -> {
                reviewBoxLayoutParams.setMargins(24.dp, 16.dp, 24.dp, 0)
                binding.viewDetailRatingBackground.layoutParams = reviewBoxLayoutParams
            }
            false -> {
                reviewBoxLayoutParams.setMargins(24.dp, 44.dp, 24.dp, 0)
                binding.viewDetailRatingBackground.layoutParams = reviewBoxLayoutParams
            }
        }
    }

    private fun setDateTopMargin(isMemoEmpty: Boolean) {
        val dateLayoutParams =
            binding.txtDetailCreatedDate.layoutParams as ConstraintLayout.LayoutParams
        when (isMemoEmpty) {
            true -> dateLayoutParams.setMargins(0, 24.dp, 20.dp, 0)
            false -> dateLayoutParams.setMargins(0, 80.dp, 20.dp, 0)
        }
        binding.txtDetailCreatedDate.layoutParams = dateLayoutParams
    }

    private fun setCommentText(preference: Int) {
        val comment = Preference.setCommentByPreference(preference)
        binding.txtDetailComment.text = comment
    }
}