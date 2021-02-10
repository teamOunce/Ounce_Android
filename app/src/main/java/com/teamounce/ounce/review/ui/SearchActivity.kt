package com.teamounce.ounce.review.ui

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.teamounce.ounce.R
import com.teamounce.ounce.base.BindingActivity
import com.teamounce.ounce.databinding.ActivitySearchBinding
import com.teamounce.ounce.review.adapter.ListFoodSearchAdapter
import com.teamounce.ounce.review.model.CatFood
import com.teamounce.ounce.review.viewmodel.SearchViewModel
import com.teamounce.ounce.util.StatusBarUtil

class SearchActivity : BindingActivity<ActivitySearchBinding>(R.layout.activity_search) {
    private val searchViewModel: SearchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.apply {
            lifecycleOwner = this@SearchActivity
            viewModel = searchViewModel
        }
        StatusBarUtil.setStatusBar(this)
        setObserver()
        setUIListener()
        val data = mutableListOf<CatFood>()
        addDummyData(data)

        val searchLinearAdapter = ListFoodSearchAdapter(data.toMutableList(), ListFoodSearchAdapter.TYPE_LINEAR)
        binding.rvReviewSearchLinear.apply {
            adapter = searchLinearAdapter
            setHasFixedSize(true)
        }

        setSearchTypeChangeListener(searchLinearAdapter, data)


    }

    private fun setObserver() {
        searchViewModel.isListSearch.observe(this, { isList ->
            binding.apply {
                btnLayoutList.isActivated = isList
                btnLayoutGrid.isActivated = !isList
            }
        })

    }

    private fun setUIListener() {
        binding.etReviewSearch.setOnFocusChangeListener { view, hasFocus ->
            when(hasFocus) {
                true -> view.background.setTint(resources.getColor(R.color.gray2, null))
                false -> view.background.setTint(resources.getColor(R.color.orangey_red, null))
            }
        }
    }

    private fun setSearchTypeChangeListener(
        searchLinearAdapter: ListFoodSearchAdapter,
        data: MutableList<CatFood>
    ) {
        binding.btnLayoutList.setOnClickListener {
            searchLinearAdapter.datas.clear()
            searchLinearAdapter.datas = data.toMutableList()
            binding.rvReviewSearchLinear.adapter = searchLinearAdapter
            searchLinearAdapter.changeLayout(ListFoodSearchAdapter.TYPE_LINEAR)
            binding.rvReviewSearchLinear.layoutManager = LinearLayoutManager(this)
            searchLinearAdapter.notifyDataSetChanged()
        }

        binding.btnLayoutGrid.setOnClickListener {
            searchLinearAdapter.datas.clear()
            searchLinearAdapter.datas = data.toMutableList()
            binding.rvReviewSearchLinear.adapter = searchLinearAdapter
            searchLinearAdapter.changeLayout(ListFoodSearchAdapter.TYPE_GRID)
            binding.rvReviewSearchLinear.layoutManager = GridLayoutManager(this, 3)
            searchLinearAdapter.notifyDataSetChanged()
        }
    }

    private fun addDummyData(data: MutableList<CatFood>) {
        data.apply {
            add(
                CatFood(
                    img_cat_food = "https://www.meijer.com/content/dam/meijer/product/0082/92/7450/22/0082927450225_1_A1C1_1200.png",
                    txt_company = "Brand",
                    txt_cat_food = "Cat Food Nyummy"
                )
            )
            add(
                CatFood(
                    img_cat_food = "https://www.meijer.com/content/dam/meijer/product/0082/92/7450/22/0082927450225_1_A1C1_1200.png",
                    txt_company = "Brand",
                    txt_cat_food = "Cat Food Nyummy"
                )
            )
            add(
                CatFood(
                    img_cat_food = "https://www.meijer.com/content/dam/meijer/product/0082/92/7450/22/0082927450225_1_A1C1_1200.png",
                    txt_company = "Brand",
                    txt_cat_food = "Cat Food Nyummy"
                )
            )
            add(
                CatFood(
                    img_cat_food = "https://www.meijer.com/content/dam/meijer/product/0082/92/7450/22/0082927450225_1_A1C1_1200.png",
                    txt_company = "Brand",
                    txt_cat_food = "Cat Food Nyummy"
                )
            )
            add(
                CatFood(
                    img_cat_food = "https://www.meijer.com/content/dam/meijer/product/0082/92/7450/22/0082927450225_1_A1C1_1200.png",
                    txt_company = "Brand",
                    txt_cat_food = "Cat Food Nyummy"
                )
            )
        }
    }

}