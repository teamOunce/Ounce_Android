package com.teamounce.ounce.review.ui

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.teamounce.ounce.R
import com.teamounce.ounce.base.BindingActivity
import com.teamounce.ounce.databinding.ActivitySearchBinding
import com.teamounce.ounce.review.adapter.ResultItemDecoration
import com.teamounce.ounce.review.adapter.SearchAdapter
import com.teamounce.ounce.review.model.ResponseSearch
import com.teamounce.ounce.review.viewmodel.SearchViewModel
import com.teamounce.ounce.util.StatusBarUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchActivity : BindingActivity<ActivitySearchBinding>(R.layout.activity_search) {
    private val searchViewModel: SearchViewModel by viewModels()
    private lateinit var searchAdapter: SearchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StatusBarUtil.setStatusBar(this)
        binding.apply {
            lifecycleOwner = this@SearchActivity
            viewModel = searchViewModel
        }
        searchAdapter = SearchAdapter(
            layoutType = SearchAdapter.TYPE_LINEAR,
            itemClickListener = object : SearchAdapter.ItemClickListener {
                override fun setOnClickListener(catFood: ResponseSearch.Data) {
                    val intent = Intent(this@SearchActivity, ReviewActivity::class.java)
                    intent.putExtra("catFood", catFood)
                    startActivity(intent)
                }
            })
        binding.rvReviewSearchLinear.apply {
            adapter = searchAdapter
            setHasFixedSize(true)
            addItemDecoration(ResultItemDecoration())
        }
        setObserver()
        setUIListener()
        setSearchTypeChangeListener()
    }

    private fun setObserver() {
        searchViewModel.isListSearch.observe(this) { isList ->
            binding.apply {
                btnLayoutList.isActivated = isList
                btnLayoutGrid.isActivated = !isList
            }
        }
        searchViewModel.resultList.observe(this) {
            if (it.isEmpty()) {
                binding.clayoutNoResult.visibility = View.VISIBLE
                binding.clayoutSearch.visibility = View.GONE
            } else {
                searchAdapter.replaceList(it)
                binding.clayoutNoResult.visibility = View.GONE
                binding.clayoutSearch.visibility = View.VISIBLE
            }
        }
    }

    private fun setUIListener() {
        binding.etReviewSearch.setOnFocusChangeListener { view, hasFocus ->
            when (hasFocus) {
                true -> view.background.setTint(resources.getColor(R.color.orangey_red, null))
                else -> view.background.setTint(resources.getColor(R.color.gray2, null))
            }
        }
        binding.imgReviewBack.setOnClickListener { finish() }
        binding.etReviewSearch.setOnKeyListener { _, keyCode, keyEvent ->
            if (keyEvent.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                searchViewModel.search()
                true
            } else {
                false
            }
        }
    }

    private fun setSearchTypeChangeListener() {
        binding.btnLayoutList.setOnClickListener {
            searchAdapter.changeLayout(SearchAdapter.TYPE_LINEAR)
            binding.rvReviewSearchLinear.layoutManager = LinearLayoutManager(this)
        }

        binding.btnLayoutGrid.setOnClickListener {
            searchAdapter.changeLayout(SearchAdapter.TYPE_GRID)
            binding.rvReviewSearchLinear.layoutManager = GridLayoutManager(this, 3)
        }
    }

}