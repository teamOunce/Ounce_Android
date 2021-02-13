package com.teamounce.ounce.review.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.teamounce.ounce.R
import com.teamounce.ounce.databinding.ItemFoodSearchGridBinding
import com.teamounce.ounce.databinding.ItemFoodSearchLinearBinding
import com.teamounce.ounce.review.model.ResponseSearch

class SearchAdapter(
    private val itemClickListener: ItemClickListener,
    private var layoutType: Int
) : RecyclerView.Adapter<SearchAdapter.ListFoodSearchViewHolder>() {
    private var searchResultList = mutableListOf<ResponseSearch.Data>()

    override fun getItemViewType(position: Int): Int {
        return layoutType
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListFoodSearchViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        when (viewType) {
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
        holder.onBind(searchResultList[position])
    }

    override fun getItemCount() = searchResultList.size

    fun changeLayout(layoutType: Int) {
        this.layoutType = layoutType
        val tempList = searchResultList.toMutableList()
        searchResultList.clear()
        searchResultList = tempList
        notifyDataSetChanged()
    }

    inner class ListFoodSearchViewHolder(private val binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(catFood: ResponseSearch.Data) {
            when(binding) {
                is ItemFoodSearchGridBinding -> binding.catFood = catFood
                is ItemFoodSearchLinearBinding -> binding.catFood = catFood
            }
            binding.root.setOnClickListener { itemClickListener.setOnClickListener(catFood) }
        }
    }

    fun replaceList(resultList: List<ResponseSearch.Data>) {
        searchResultList = resultList.toMutableList()
        notifyDataSetChanged()
    }

    interface ItemClickListener {
        fun setOnClickListener(catFood: ResponseSearch.Data)
    }

    companion object {
        const val TYPE_LINEAR = 1
        const val TYPE_GRID = 2
    }
}