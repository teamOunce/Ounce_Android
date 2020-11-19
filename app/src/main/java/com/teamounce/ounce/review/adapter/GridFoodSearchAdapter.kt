package com.teamounce.ounce.review.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.teamounce.ounce.R
import com.teamounce.ounce.databinding.ItemFoodSearchGridBinding
import com.teamounce.ounce.review.model.CatFood

class GridFoodSearchAdapter(var datas: MutableList<CatFood>) :
    RecyclerView.Adapter<GridFoodSearchAdapter.GridFoodSearchViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridFoodSearchViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemFoodSearchGridBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_food_search_grid, parent, false)
        return GridFoodSearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GridFoodSearchViewHolder, position: Int) {

    }

    override fun getItemCount() = datas.size

    inner class GridFoodSearchViewHolder(private val binding: ItemFoodSearchGridBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(catFood: CatFood) {
            binding.catFood = catFood
        }
    }
}