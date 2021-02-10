package com.teamounce.ounce.review.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.teamounce.ounce.R
import com.teamounce.ounce.databinding.ItemCatfoodSliderBinding

class CatFoodSliderAdapter : RecyclerView.Adapter<CatFoodSliderAdapter.CatFoodViewHolder>() {
    private var catFoodUrlList = listOf<String>()

    class CatFoodViewHolder(private val binding: ItemCatfoodSliderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(url: String) { binding.url = url }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatFoodViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ItemCatfoodSliderBinding =
            DataBindingUtil.inflate(inflater, R.layout.item_catfood_slider, parent, false)
        return CatFoodViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CatFoodViewHolder, position: Int) { holder.bind(catFoodUrlList[position]) }

    override fun getItemCount(): Int = catFoodUrlList.size

    fun replaceList(newCatFoodList: List<String>) {
        catFoodUrlList = newCatFoodList
        notifyDataSetChanged()
    }
}