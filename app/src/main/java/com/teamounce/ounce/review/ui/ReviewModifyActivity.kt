package com.teamounce.ounce.review.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.OpenableColumns
import android.util.Log
import android.view.MotionEvent
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.chip.Chip
import com.teamounce.ounce.R
import com.teamounce.ounce.base.BindingActivity
import com.teamounce.ounce.component.OunceOneButtonDialog
import com.teamounce.ounce.databinding.ActivityReviewModifyBinding
import com.teamounce.ounce.feed.ui.Comment
import com.teamounce.ounce.review.adapter.CatFoodSliderAdapter
import com.teamounce.ounce.review.model.ImageInfo
import com.teamounce.ounce.review.viewmodel.ReviewViewModel
import com.teamounce.ounce.util.ChipClient
import com.teamounce.ounce.util.StatusBarUtil
import com.teamounce.ounce.util.asMultipart
import com.teamounce.ounce.util.makeMultiPart
import dagger.hilt.android.AndroidEntryPoint
import gun0912.tedimagepicker.builder.TedImagePicker
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

@AndroidEntryPoint
class ReviewModifyActivity :
    BindingActivity<ActivityReviewModifyBinding>(R.layout.activity_review_modify) {
    private val reviewViewModel by viewModels<ReviewViewModel>()
    private lateinit var imageSliderAdapter: CatFoodSliderAdapter
    private val preferenceCheckBoxList = mutableListOf<CheckBox>()


    private val reviewModifySuccessDialog by lazy {
        OunceOneButtonDialog(
            this,
            "수정 완료되었습니다!",
            "",
            "확인",
            false
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.apply {
            lifecycleOwner = this@ReviewModifyActivity
            activity = this@ReviewModifyActivity
            viewModel = reviewViewModel
        }
        StatusBarUtil.setStatusBar(this)
        val reviewIndex = intent.getIntExtra("reviewIndex", 0)
        initView()
        reviewViewModel.setEmptyImage(
            MultipartBody.Part.createFormData(
                "image",
                "",
                "".toRequestBody("image/png".toMediaTypeOrNull())
            )
        )
        reviewViewModel.reviewIndex = reviewIndex
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
        binding.vpRecordSlider.adapter = imageSliderAdapter
        Log.i("리뷰 수정", "adapter 세팅 완료")
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setUIListener() {
        binding.imgReviewBack.setOnClickListener { finish() }
        binding.imgRecordAddImage.setOnClickListener {
            TedImagePicker.with(this)
                .start { uri ->
                    Log.d("TAG URI", uri.toString())
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

        binding.etRecordMemo.setOnTouchListener { v, event ->
            v.parent.requestDisallowInterceptTouchEvent(true)
            when (event.action and MotionEvent.ACTION_MASK) {
                MotionEvent.ACTION_UP -> v.parent.requestDisallowInterceptTouchEvent(false)
            }
            false
        }
    }

    private fun subscribeData() {
        reviewViewModel.reviewInfo.observe(this) {
            addCheckBoxView(it.preference)
            binding.etRecordMemo.setText(it.memo)
            listOf(it.productImg, it.myImg)
                .filter { imgString -> imgString != "" }
                .map { notNullImgString -> ImageInfo(notNullImgString, true) }
                .also { img ->
                    imageSliderAdapter.replaceList(img)
                    if (img.size > 1)
                        binding.vpRecordSlider.post {
                            binding.vpRecordSlider.setCurrentItem(img.size - 1, false)
                        }
                }
            val selectedTag = listOf(it.tag1, it.tag2, it.tag3)
                .filter { tag -> tag != "" }
            reviewViewModel.initTags(selectedTag)
            Log.i("리뷰 수정", "리뷰 인포 데이터 세팅 완")
        }
        reviewViewModel.warningMessage.observe(this) { it.toast() }
        reviewViewModel.tagList.observe(this) {
            it.asSequence()
                .map { "#${it.tag}" }
                .map { it.toChip() }
                .map { it.init() }
                .forEach { binding.chipgroupRecordTag.addView(it) }
        }
        reviewViewModel.result.observe(this) {
            if (it.data == null) {
                Toast.makeText(this, "리뷰 등록이 실패되었습니다.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "리뷰 등록을 성공했습니다.", Toast.LENGTH_SHORT).show()
//                ReviewCompleteFragment(
//                    reviewViewModel.preference.toInt(),
//                    object : ReviewCompleteFragment.DisMissClickListener {
//                        override fun onClick(context: Context) {
//                            setResult(Activity.RESULT_OK)
//                            finish()
//                        }
//
//                        override fun onClickMoreReview() {
//                            setResult(Activity.RESULT_OK)
//                            finish()
//                        }
//                    }
//                ).show(supportFragmentManager, "ReviewComplete")
                reviewModifySuccessDialog.apply {
                    setOnDialogClickListener(object : OunceOneButtonDialog.ClickListener {
                        override fun setOnClickOk(dialog: Dialog) {
                            dialog.dismiss()
                            setResult(Activity.RESULT_OK)
                            finish()
                        }
                    })
                }.show()
            }
        }
    }

    private fun String.toast() {
        Toast.makeText(this@ReviewModifyActivity, this, Toast.LENGTH_SHORT).show()
    }

    private fun String.toChip(): Chip =
        ChipClient.create(layoutInflater).also { it.text = this }

    private fun chipCheckedChangeListener(): CompoundButton.OnCheckedChangeListener {
        return CompoundButton.OnCheckedChangeListener { compoundButton, checked ->
            if (checked) {
                reviewViewModel.addTag(compoundButton.text.substring(1))
                if (reviewViewModel.isTagEntryFull()) {
                    Log.d("TAG", "TAG FULL ${compoundButton.text}: $checked")
                    compoundButton.isChecked = false
                    reviewViewModel.deleteTag(compoundButton.text.substring(1))
                }
            } else {
                reviewViewModel.deleteTag(compoundButton.text.substring(1))
            }
        }
    }

    private fun Chip.init(): Chip {
        this.setOnCheckedChangeListener(chipCheckedChangeListener())
        setChecked(this)
        return this
    }

    private fun setChecked(chip: Chip) {
        chip.isChecked = reviewViewModel.isContained(chip.text.substring(1))
    }

    private fun makeMultiPartBody(uri: Uri) {
        // val file = getFile(this, uri)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val partBody = uri.asMultipart("image", contentResolver)
            Log.d("TAG", (partBody == null).toString())
            if (partBody != null) {
                reviewViewModel.setImageFile(partBody)
            }
        } else {
            lifecycleScope.launch {
                try {
                    val result = uri.makeMultiPart(this@ReviewModifyActivity)
                    if (result != null)
                        reviewViewModel.setImageFile(result)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

//    private fun makeMultiPartBody(uri: Uri) {
//        val file = getFile(this, uri)
//        val part = MultipartBody.Part.createFormData(
//            "image",
//            file.name,
//            file.asRequestBody("image/png".toMediaTypeOrNull())
//        )
//        reviewViewModel.setImageFile(part)
//    }

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

    /** Add CheckBox View */
    private fun addCheckBoxView(preference: Int) {
        preferenceCheckBoxList.run {
            add(binding.checkboxPreference1.apply { isChecked = preference >= 1 })
            add(binding.checkboxPreference2.apply { isChecked = preference >= 2 })
            add(binding.checkboxPreference3.apply { isChecked = preference >= 3 })
            add(binding.checkboxPreference4.apply { isChecked = preference >= 4 })
            add(binding.checkboxPreference5.apply { isChecked = preference >= 5 })
        }
    }


    fun onClickPreferenceCheckBox(preference: Int?) {
        if (preference == null || preferenceCheckBoxList.size < preference)
            return

        reviewViewModel.preference = preference.toFloat()
        binding.txtRecordPreferenceExplain.text = Comment.of(preference)

        preferenceCheckBoxList.forEachIndexed { index, checkBox ->
            checkBox.isChecked = index < preference
        }
    }
}