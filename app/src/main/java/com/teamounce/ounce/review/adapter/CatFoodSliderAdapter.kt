package com.teamounce.ounce.review.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.teamounce.ounce.R
import com.teamounce.ounce.databinding.ItemCatfoodSliderBinding
import com.teamounce.ounce.review.model.ImageInfo

class CatFoodSliderAdapter : RecyclerView.Adapter<CatFoodSliderAdapter.CatFoodViewHolder>() {
    private var catFoodUrlList = listOf<ImageInfo>()

    class CatFoodViewHolder(private val binding: ItemCatfoodSliderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(imageInfo: ImageInfo) {
            binding.url = imageInfo.image
            binding.isUrl = imageInfo.isUrl
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatFoodViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ItemCatfoodSliderBinding =
            DataBindingUtil.inflate(inflater, R.layout.item_catfood_slider, parent, false)
        return CatFoodViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CatFoodViewHolder, position: Int) {
        holder.bind(catFoodUrlList[position])
    }

    override fun getItemCount(): Int = catFoodUrlList.size

    fun replaceList(newCatFoodList: List<ImageInfo>) {
        catFoodUrlList = newCatFoodList
        notifyDataSetChanged()
    }
}