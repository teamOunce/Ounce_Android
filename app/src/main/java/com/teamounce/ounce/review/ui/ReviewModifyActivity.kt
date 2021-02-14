package com.teamounce.ounce.review.ui

import android.annotation.SuppressLint
import android.content.Context
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
import com.teamounce.ounce.databinding.ActivityReviewModifyBinding
import com.teamounce.ounce.review.adapter.CatFoodSliderAdapter
import com.teamounce.ounce.review.model.ImageInfo
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
class ReviewModifyActivity :
    BindingActivity<ActivityReviewModifyBinding>(R.layout.activity_review_modify) {
    private val reviewViewModel by viewModels<ReviewViewModel>()
    private lateinit var imageSliderAdapter: CatFoodSliderAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.apply {
            lifecycleOwner = this@ReviewModifyActivity
            viewModel = reviewViewModel
        }
        StatusBarUtil.setStatusBar(this)
        val reviewIndex = intent.getIntExtra("reviewIndex", 0)
        initView()
        reviewViewModel.getReviewInfo(reviewIndex)
        reviewViewModel.getTags()
    }

    private fun initView() {
        setAdapter()
        setUIListener()
        subscribeData()
    }

    private fun setAdapter() {
        imageSliderAdapter = CatFoodSliderAdapter()
    }

    private fun setUIListener() {
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
                            ImageInfo(reviewViewModel.reviewInfo.value!!.productImg, true),
                            ImageInfo(uri.toString(), false)
                        )
                    )
                    makeMultiPartBody(uri)
                }
        }
        binding.btnSubmit.setOnClickListener { reviewViewModel.modifyReview() }
        binding.imgRecordTooltip.setOnClickListener {
            ToolTipFragment().show(supportFragmentManager, "ToolTip")
        }
    }

    private fun subscribeData() {
        reviewViewModel.reviewInfo.observe(this) {
            binding.ratingRecordPreference.setStar(it.preference.toFloat())
            binding.etRecordMemo.setText(it.memo)
        }
        reviewViewModel.warningMessage.observe(this) { it.toast() }
        reviewViewModel.tagList.observe(this) {
            it.asSequence()
                .map { "#${it.tag}" }
                .map { it.toChip() }
                .map { it.init() }
                .forEach { binding.chipgroupRecordTag.addView(it) }
        }
    }

    private fun String.toast() {
        Toast.makeText(this@ReviewModifyActivity, this, Toast.LENGTH_SHORT).show()
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

    private fun Chip.init(): Chip {
        this.setOnCheckedChangeListener(chipCheckedChangeListener())
        setChecked(this)
        return this
    }

    private fun setChecked(chip: Chip) {
        val selectedTagList =
            with(reviewViewModel.reviewInfo.value!!) { listOf(this.tag1, this.tag2, this.tag3) }
        chip.isSelected = selectedTagList.contains(chip.text)
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