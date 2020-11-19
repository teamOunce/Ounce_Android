package com.teamounce.ounce.review.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.teamounce.ounce.R
import com.teamounce.ounce.databinding.ItemFoodSearchGridBinding
import com.teamounce.ounce.databinding.ItemFoodSearchLinearBinding
import com.teamounce.ounce.review.model.CatFood

class ListFoodSearchAdapter(
    var datas: MutableList<CatFood>,
    var layoutType: Int
) :
    RecyclerView.Adapter<ListFoodSearchAdapter.ListFoodSearchViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListFoodSearchViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        when (layoutType) {
            TYPE_LINEAR -> {
                val binding: ItemFoodSearchLinearBinding =
                    DataBindingUtil.inflate(
                        layoutInflater,
                        R.layout.item_food_search_linear,
                        parent,
                        false
                    )
                return ListFoodSearchViewHolder(binding)
            }
            else -> {
                val binding: ItemFoodSearchGridBinding =
                    DataBindingUtil.inflate(
                        layoutInflater,
                        R.layout.item_food_search_grid,
                        parent,
                        false
                    )
                return ListFoodSearchViewHolder(binding)
            }
        }

    }

    override fun onBindViewHolder(holder: ListFoodSearchViewHolder, position: Int) {
        holder.onBind(datas[position])
    }

    override fun getItemCount() = datas.size

    fun changeLayout(layoutType: Int) {
        this.layoutType = layoutType
    }

    inner class ListFoodSearchViewHolder(private val binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(catFood: CatFood) {
            when(binding) {
                is ItemFoodSearchGridBinding -> binding.catFood = catFood
                is ItemFoodSearchLinearBinding -> binding.catFood = catFood
            }
        }
    }

    companion object {
        const val TYPE_LINEAR = 1
        const val TYPE_GRID = 2
    }
}