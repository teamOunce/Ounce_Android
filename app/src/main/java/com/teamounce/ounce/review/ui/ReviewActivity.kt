package com.teamounce.ounce.review.ui

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.CompoundButton
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContentProviderCompat.requireContext
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
import gun0912.tedimagepicker.builder.TedImagePicker
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream
import java.io.File

@AndroidEntryPoint
class ReviewActivity : BindingActivity<ActivityReviewBinding>(R.layout.activity_review) {
    private val reviewViewModel: ReviewViewModel by viewModels()
    private lateinit var imageSliderAdapter: CatFoodSliderAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StatusBarUtil.setStatusBar(this)
        val initCatFoodData = intent.getParcelableExtra<ResponseSearch.Data>("catFood")
            ?: throw IllegalArgumentException("왜 없어 에반데")
        setUIListener(initCatFoodData)
        setObserver()
        reviewViewModel.getTags()
        imageSliderAdapter = CatFoodSliderAdapter()
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

    private fun setUIListener(catFood: ResponseSearch.Data) {
        binding.imgReviewBack.setOnClickListener { finish() }
        binding.ratingRecordPreference.setOnRatingChangeListener {
            reviewViewModel.preference = it
            binding.btnSubmit.isEnabled = true
        }
        binding.imgRecordAddImage.setOnClickListener {
            TedImagePicker.with(this)
                .start { uri ->
                    imageSliderAdapter.replaceList(listOf(catFood.productImg, uri.toString()))
                    makeMultiPartBody(uri)
                }
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
                if (reviewViewModel.isTagsFull) { compoundButton.isChecked = false }
                else { reviewViewModel.addTag(compoundButton.text.toString()) }
            } else {
                reviewViewModel.deleteTag(compoundButton.text.toString())
            }
        }
    }

    private fun makeMultiPartBody(uri: Uri) {
        val options = BitmapFactory.Options()
        val inputStream = contentResolver.openInputStream(uri)
        val bitmap = BitmapFactory.decodeStream(inputStream, null, options)
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap!!.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream)
        val photoBody =
            byteArrayOutputStream.toByteArray()
                .toRequestBody(
                    "image/png".toMediaTypeOrNull(),
                    0, byteArrayOutputStream.size()
                )
//        RequestBody.create(
//            "image/png".toMediaTypeOrNull(),
//            byteArrayOutputStream.toByteArray()
//        )
        val part = MultipartBody.Part.createFormData(
            "image",
            File(uri.toString()).name,
            photoBody
        )
        reviewViewModel.setImageFile(part)
    }
}