package com.teamounce.ounce.review.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.teamounce.ounce.R
import com.teamounce.ounce.databinding.ActivitySearchBinding
import com.teamounce.ounce.review.adapter.ListFoodSearchAdapter
import com.teamounce.ounce.review.model.CatFood
import com.teamounce.ounce.review.viewmodel.SearchViewModel

class SearchActivity : AppCompatActivity() {
    private val searchViewModel : SearchViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivitySearchBinding = DataBindingUtil.setContentView(this, R.layout.activity_search)
        binding.lifecycleOwner = this
        binding.searchViewModel = searchViewModel

        val data = mutableListOf<CatFood>()
        addDummyData(data)

        val searchLinearAdapter = ListFoodSearchAdapter(data.toMutableList(), ListFoodSearchAdapter.TYPE_LINEAR)
        binding.rvReviewSearchLinear.adapter = searchLinearAdapter
        binding.rvReviewSearchLinear.setHasFixedSize(true)

        binding.btnLayoutList.setOnClickListener {
            searchViewModel.changeSearchTypeToList()
            searchLinearAdapter.datas.clear()
            searchLinearAdapter.datas = data.toMutableList()
            binding.rvReviewSearchLinear.adapter = searchLinearAdapter
            searchLinearAdapter.changeLayout(ListFoodSearchAdapter.TYPE_LINEAR)
            binding.rvReviewSearchLinear.layoutManager = LinearLayoutManager(this)
            searchLinearAdapter.notifyDataSetChanged()
        }

        binding.btnLayoutGrid.setOnClickListener {
            searchViewModel.changeSearchTypeToGrid()
            searchLinearAdapter.datas.clear()
            searchLinearAdapter.datas = data.toMutableList()
            binding.rvReviewSearchLinear.adapter = searchLinearAdapter
            searchLinearAdapter.changeLayout(ListFoodSearchAdapter.TYPE_GRID)
            binding.rvReviewSearchLinear.layoutManager = GridLayoutManager(this, 3)
            searchLinearAdapter.notifyDataSetChanged()
        }

        searchViewModel.isListSearch.observe(this, { isList ->
            if(isList) {
                binding.apply {
                    btnLayoutList.isActivated = true
                    btnLayoutGrid.isActivated = false
                }
            } else {
                binding.apply {
                    btnLayoutList.isActivated = false
                    btnLayoutGrid.isActivated = true
                }
            }
        })
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