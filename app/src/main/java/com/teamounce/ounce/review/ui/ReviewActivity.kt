package com.teamounce.ounce.review.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.util.Log
import android.widget.CompoundButton
import android.widget.Toast
import androidx.activity.viewModels
import com.google.android.material.chip.Chip
import com.teamounce.ounce.R
import com.teamounce.ounce.base.BindingActivity
import com.teamounce.ounce.databinding.ActivityReviewBinding
import com.teamounce.ounce.feed.ui.FeedActivity
import com.teamounce.ounce.review.adapter.CatFoodSliderAdapter
import com.teamounce.ounce.review.model.ImageInfo
import com.teamounce.ounce.review.model.ResponseSearch
import com.teamounce.ounce.review.viewmodel.ReviewViewModel
import com.teamounce.ounce.util.ChipFactory
import com.teamounce.ounce.util.StatusBarUtil
import dagger.hilt.android.AndroidEntryPoint
import gun0912.tedimagepicker.builder.TedImagePicker
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
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
        }
        binding.btnSubmit.setOnClickListener { reviewViewModel.registerReview(catFood) }
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
        val file = getFile(this, uri)
        val part = MultipartBody.Part.createFormData(
            "image",
            file.name,
            file.asRequestBody("image/png".toMediaTypeOrNull())
        )
        reviewViewModel.setImageFile(part)
    }

    private fun getFile(context: Context, uri: Uri): File {
        val destinationFilename =
            File(context.filesDir.path + File.separatorChar + queryName(context, uri))
        try {
            context.contentResolver.openInputStream(uri).use { ins ->
                if (ins != null) {
                    createFileFromStream(ins, destinationFilename)
                }
            }
        } catch (ex: Exception) {
            Log.e("Save File", ex.message!!)
            ex.printStackTrace()
        }
        return destinationFilename
    }

    private fun createFileFromStream(ins: InputStream, destination: File?) {
        try {
            FileOutputStream(destination).use { os ->
                val buffer = ByteArray(4096)
                var length: Int
                length = ins.read(buffer)
                while (length > 0) {
                    os.write(buffer, 0, length)
                    length = ins.read(buffer)
                }
                os.flush()
            }
        } catch (ex: Exception) {
            Log.e("Save File", ex.message!!)
            ex.printStackTrace()
        }
    }

    @SuppressLint("Recycle")
    private fun queryName(context: Context, uri: Uri): String {
        val returnCursor: Cursor =
            context.contentResolver.query(uri, null, null, null, null)!!
        val nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        returnCursor.moveToFirst()
        val name = returnCursor.getString(nameIndex)
        returnCursor.close()
        return name
    }
}