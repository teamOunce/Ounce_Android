package com.teamounce.ounce.review.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.Log
import android.widget.CompoundButton
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import com.teamounce.ounce.R
import com.teamounce.ounce.base.BindingActivity
import com.teamounce.ounce.databinding.ActivityReviewBinding
import com.teamounce.ounce.feed.ui.Comment
import com.teamounce.ounce.feed.ui.FeedActivity
import com.teamounce.ounce.review.adapter.CatFoodSliderAdapter
import com.teamounce.ounce.review.model.ImageInfo
import com.teamounce.ounce.review.model.ResponseSearch
import com.teamounce.ounce.review.viewmodel.ReviewViewModel
import com.teamounce.ounce.util.*
import dagger.hilt.android.AndroidEntryPoint
import gun0912.tedimagepicker.builder.TedImagePicker
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream


@AndroidEntryPoint
class ReviewActivity : BindingActivity<ActivityReviewBinding>(R.layout.activity_review) {
    private val reviewViewModel: ReviewViewModel by viewModels()
    private lateinit var imageSliderAdapter: CatFoodSliderAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.lifecycleOwner = this
        binding.viewModel = reviewViewModel
        StatusBarUtil.setStatusBar(this)
        val initCatFoodData = intent.getParcelableExtra<ResponseSearch.Data>("catFood")
            ?: throw IllegalArgumentException("왜 없어 에반데")
        reviewViewModel.setEmptyImage(
            MultipartBody.Part.createFormData(
                "image",
                "",
                "".toRequestBody("image/png".toMediaTypeOrNull())
            )
        )
        setUIListener(initCatFoodData)
        setObserver()
        reviewViewModel.getTags()
        imageSliderAdapter = CatFoodSliderAdapter()
        initSetting(initCatFoodData)
    }

    private fun initSetting(catFood: ResponseSearch.Data) {
        imageSliderAdapter.replaceList(listOf(ImageInfo(catFood.productImg, true)))
        binding.apply {
            txtRecordBrand.text = catFood.manufacturer
            txtRecordFood.text = catFood.productName
            vpRecordSlider.adapter = imageSliderAdapter
            dotsIndicator.setViewPager2(binding.vpRecordSlider)
        }
    }

    private fun setUIListener(catFood: ResponseSearch.Data) {
        binding.imgReviewBack.setOnClickListener { finish() }
        binding.ratingRecordPreference.setOnRatingChangeListener {
            reviewViewModel.preference = it
            binding.btnSubmit.isEnabled = true
            binding.txtRecordPreferenceExplain.text = Comment.of(it.toInt())
        }
        binding.imgRecordAddImage.setOnClickListener {
            TedImagePicker.with(this)
                .start { uri ->
                    imageSliderAdapter.replaceList(
                        listOf(
                            ImageInfo(catFood.productImg, true),
                            ImageInfo(uri.toString(), false)
                        )
                    )
                    makeMultiPartBody(uri)
                }
//            Toast.makeText(this, "기능 준비중입니다", Toast.LENGTH_SHORT).show()
        }
        binding.btnSubmit.setOnClickListener {
            Log.e("Click", "Call Register")
            reviewViewModel.registerReview(catFood)
        }
        binding.imgRecordTooltip.setOnClickListener {
            ToolTipFragment().show(supportFragmentManager, "ToolTip")
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
        reviewViewModel.result.observe(this) {
            if (it.data == null) {
                Toast.makeText(this, "리뷰 등록이 실패되었습니다.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "리뷰 등록을 성공했습니다.", Toast.LENGTH_SHORT).show()
                ReviewCompleteFragment(
                    reviewViewModel.preference.toInt(),
                    object : ReviewCompleteFragment.DisMissClickListener {
                        override fun onClick(context: Context) {
                            startActivity(Intent(context, FeedActivity::class.java))
                            finish()
                        }

                        override fun onClickMoreReview() {
                            setResult(Activity.RESULT_OK)
                            finish()
                        }
                    }
                ).show(supportFragmentManager, "ReviewComplete")
            }
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

    private fun makeMultiPartBody(uri: Uri) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            Log.i("TAG","MY BUILD SDK 29 UP")
            val partBody = uri.asMultipart("image", contentResolver)
            if (partBody != null)
                reviewViewModel.setImageFile(partBody)
            else
                Log.i("TAG", "29 UP MULTI PART NULL")
        }
        else {
            Log.i("TAG","MY BUILD SDK 29 DOWN")
            lifecycleScope.launch {
                val result = uri.makeMultiPart(this@ReviewActivity)
                if (result != null)
                    reviewViewModel.setImageFile(result)
            }
        }

    }


}