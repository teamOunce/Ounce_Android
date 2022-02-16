package com.teamounce.ounce.review.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.teamounce.ounce.R
import com.teamounce.ounce.base.BindingActivity
import com.teamounce.ounce.databinding.ActivitySearchBinding
import com.teamounce.ounce.feed.ui.FeedActivity
import com.teamounce.ounce.feed.ui.FoodDetailActivity
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
    private lateinit var requestReviewActivity: ActivityResultLauncher<Intent>

    private val mImm by lazy {
        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    }

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
                    if (catFood.reviewIndex == null) {
                        val intent = Intent(this@SearchActivity, ReviewActivity::class.java)
                        intent.putExtra("catFood", catFood)
                        if (this@SearchActivity::requestReviewActivity.isInitialized) {
                            requestReviewActivity.launch(intent)
                        } else {
                            startActivity(intent)
                        }
                    } else {
                        startActivity(
                            Intent(this@SearchActivity, FeedActivity::class.java)
                                .apply {
                                    putExtra("reviewIndex", catFood.reviewIndex)
                                }
                        )
                    }
                }
            })
        binding.rvReviewSearchLinear.apply {
            adapter = searchAdapter
            setHasFixedSize(true)
            addItemDecoration(ResultItemDecoration())
        }
        setObserver()
        setUIListener()

        focusSearchEdt()
        initActivityResult()
    }

    private fun setObserver() {

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
        binding.imgReviewBack.setOnClickListener {
            mImm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
            finish()
        }
        binding.etReviewSearch.setOnKeyListener { _, keyCode, keyEvent ->
            if (keyEvent.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                searchViewModel.search()
                mImm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
                binding.etReviewSearch.clearFocus()
                true
            } else {
                false
            }
        }

        binding.imgReviewSearch.setOnClickListener {
            searchViewModel.search()
            mImm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
            binding.etReviewSearch.clearFocus()
        }

        /** 건의하기 버튼 클릭 시 구글 폼 이동 */
        binding.btnSuggestCatFood.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("https://forms.gle/GL6r3kNrr32e5xFR6")
            })
        }
    }

    private fun focusSearchEdt() {
        binding.etReviewSearch.requestFocus()

        mImm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)

    }

    private fun initActivityResult() {
        requestReviewActivity = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->

            if (result.resultCode == Activity.RESULT_OK) {
                searchViewModel.search()
            }

        }
    }

}