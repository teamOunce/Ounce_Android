package com.teamounce.ounce.feed.ui

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.teamounce.ounce.R
import com.teamounce.ounce.base.BindingActivity
import com.teamounce.ounce.databinding.ActivityFoodDetailBinding
import com.teamounce.ounce.feed.adapter.FoodImageSliderAdapter
import com.teamounce.ounce.feed.adapter.ReviewTagAdapter
import com.teamounce.ounce.feed.model.Review
import com.teamounce.ounce.feed.viewmodel.FeedViewModel
import com.teamounce.ounce.review.ui.ReviewModifyActivity
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
        val reviewIndex = intent.getIntExtra("reviewIndex", 0)
        feedViewModel.fetchReviewData(reviewIndex)
        setUIListener(reviewIndex)
        val tagAdapter = ReviewTagAdapter()
        binding.rvDetailTag.adapter = tagAdapter
        binding.rvDetailTag.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                super.getItemOffsets(outRect, view, parent, state)
                if (parent.getChildAdapterPosition(view) != 0) {
                    outRect.left = 2.dp
                    outRect.right = 2.dp
                } else {
                    outRect.right = 2.dp
                }
            }
        })

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
            Log.d("TAG", tagList.toString())
            tagAdapter.replaceList(tagList)
            setReviewBoxTopMargin(tagList.isEmpty())

            val imageUrlList = listOf(
                it.catFoodReview.productImg,
                it.catFoodReview.myImg
            ).filter { url -> url != "" }
            foodImageSliderAdapter.replaceList(imageUrlList)
            if (imageUrlList.size == 2) {
                binding.vpDetailImageSlider.registerOnPageChangeCallback(providePageChangeCallback())
            } else {
                binding.imgDetailBeforeImage.visibility = View.GONE
                binding.imgDetailNextImage.visibility = View.GONE
            }

            binding.txtDetailCreatedDate.setText(setDateText(it.catFoodReview))
        }
    }

    private fun setUIListener(reviewIndex: Int) {
        binding.imgDetailBack.setOnClickListener { finish() }
        binding.imgDetailNextImage.setOnClickListener {
            with(binding.vpDetailImageSlider) {
                this.setCurrentItem(this.currentItem + 1, true)
            }
        }
        binding.imgDetailBeforeImage.setOnClickListener {
            with(binding.vpDetailImageSlider) {
                this.setCurrentItem(this.currentItem - 1, true)
            }
        }
        binding.imgDetailOptions.setOnClickListener {
            val intent = Intent(this, ReviewModifyActivity::class.java)
            intent.putExtra("reviewIndex", reviewIndex)
            startActivity(intent)
        }
    }

    private fun setDateText(review: Review.CatFoodReview): String {
        return "만든 날짜: ${review.createdDate.substring(0..9)}\n" +
                "마지막 수정: ${review.updatedDate.substring(0..9)}"
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
        val comment = Comment.of(preference)
        binding.txtDetailComment.text = comment
    }

    private fun providePageChangeCallback(): ViewPager2.OnPageChangeCallback {
        return object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                when(position) {
                    0 -> {
                        binding.imgDetailBeforeImage.visibility = View.GONE
                        binding.imgDetailNextImage.visibility = View.VISIBLE
                    }
                    else -> {
                        binding.imgDetailNextImage.visibility = View.GONE
                        binding.imgDetailBeforeImage.visibility = View.VISIBLE
                    }
                }
            }
        }
    }
}