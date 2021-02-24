package com.teamounce.ounce.feed.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.teamounce.ounce.R
import com.teamounce.ounce.base.BindingActivity
import com.teamounce.ounce.databinding.ActivityTagBinding
import com.teamounce.ounce.feed.adapter.TagAdapter
import com.teamounce.ounce.feed.adapter.TagItemDecoration
import com.teamounce.ounce.feed.viewmodel.TagViewModel
import com.teamounce.ounce.util.StatusBarUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TagActivity : BindingActivity<ActivityTagBinding>(R.layout.activity_tag) {
    private val tagViewModel by viewModels<TagViewModel>()
    private lateinit var tagAdapter: TagAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    private fun initView() {
        StatusBarUtil.setStatusBar(this)
        binding.apply {
            lifecycleOwner = this@TagActivity
            viewModel = tagViewModel
        }
        setUIListener()
        setAdapter()
        subscribeObservers()
    }

    private fun setUIListener() {
        binding.imgTagBack.setOnClickListener { finish() }
    }

    private fun setAdapter() {
        tagAdapter = TagAdapter(provideDeleteButtonListener())
        binding.rvTagResult.adapter = tagAdapter
        binding.rvTagResult.addItemDecoration(TagItemDecoration())
    }

    private fun subscribeObservers() {
        val orange = resources.getColor(R.color.orange, null)
        val gray3 = resources.getColor(R.color.gray3, null)
        tagViewModel.apply {
            tagList.observe(this@TagActivity) { tagAdapter.submitList(it) }
            errorMessage.observe(this@TagActivity) { it.toast() }
            isTagMax.observe(this@TagActivity) {
                if (it) { setErrorText("이미 15개가 작성되어 있어요.", orange) }
                binding.etTagSearch.isEnabled = !it
                binding.etTagSearch.setText("")
            }
            queryWordMax.observe(this@TagActivity) {
                if(it) { setErrorText("최대 글자 수를 초과했어요.", orange) }
            }
            isExistedTag.observe(this@TagActivity) {
                if(it) { setErrorText("이미 등록한 태그에요.", orange) }
            }
            isAddEnabled.observe(this@TagActivity) {
                if(it) { setErrorText("최대 15개까지 작성할 수 있어요", gray3) }
                binding.imgTagPlus.isEnabled = it
            }
        }
    }

    private fun setErrorText(message: String, color: Int) {
        binding.txtTagHelper.apply {
            text = message
            setTextColor(color)
        }
    }

    private fun provideDeleteButtonListener() : TagAdapter.DeleteClickListener {
        return object : TagAdapter.DeleteClickListener {
            override fun setOnClickListener(tagIndex: Int) {
                tagViewModel.deleteTag(tagIndex)
            }
        }
    }

    private fun String.toast() {
        Toast.makeText(this@TagActivity, this, Toast.LENGTH_SHORT).show()
    }
}