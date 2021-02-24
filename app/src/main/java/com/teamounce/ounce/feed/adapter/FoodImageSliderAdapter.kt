package com.teamounce.ounce.feed.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.teamounce.ounce.R
import com.teamounce.ounce.databinding.ItemDetailFoodBinding

class FoodImageSliderAdapter : RecyclerView.Adapter<FoodImageSliderAdapter.FoodImageViewHolder>() {
    private var imageUrlDatas = listOf<String>()
    class FoodImageViewHolder(private val binding: ItemDetailFoodBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(url: String) {
            binding.url = url
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodImageViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemDetailFoodBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_detail_food, parent, false)
        return FoodImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FoodImageViewHolder, position: Int) {
        holder.bind(imageUrlDatas[position])
    }

    override fun getItemCount() = imageUrlDatas.size

    fun replaceList(newImageUrlDatas: List<String>) {
        imageUrlDatas = newImageUrlDatas
        notifyDataSetChanged()
    }
}