package com.teamounce.ounce.feed.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.teamounce.ounce.R
import com.teamounce.ounce.databinding.ItemFeedListBinding
import com.teamounce.ounce.feed.model.ResponseFeedListData
import com.teamounce.ounce.feed.model.ResponseFeedReviewData
import com.teamounce.ounce.util.dpFloat
import com.teamounce.ounce.util.pixel
import com.teamounce.ounce.util.pixelFloat

class FeedListAdapter : RecyclerView.Adapter<FeedListAdapter.FeedListViewHolder>() {

    var feedList = listOf<ResponseFeedReviewData.Data>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedListViewHolder {
        val binding =
            ItemFeedListBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return FeedListViewHolder(binding)
    }

    override fun getItemCount(): Int = feedList.size

    override fun onBindViewHolder(holder: FeedListViewHolder, position: Int) {
        holder.onBind(feedList[position])
    }

    inner class FeedListViewHolder(val binding: ItemFeedListBinding) :
        RecyclerView.ViewHolder(binding.root) {

            fun onBind(data : ResponseFeedReviewData.Data){
                binding.data = data
                if (data.tag1.isNotBlank()){
                    val chip = setChip(data.tag1)
                    binding.feedChipGroup.addView(chip)
                }

                if (data.tag2.isNotBlank()){
                    val chip = setChip(data.tag2)
                    binding.feedChipGroup.addView(chip)
                }

                if (data.tag3.isNotBlank()){
                    val chip = setChip(data.tag3)
                    binding.feedChipGroup.addView(chip)
                }

            }

            @SuppressLint("SetTextI18n")
            private fun setChip(tag:String) : Chip{
                val chip = Chip(itemView.context)
                chip.apply {
                    layoutDirection = View.LAYOUT_DIRECTION_LOCALE
                    text = "#${tag}"
                    textAlignment = View.TEXT_ALIGNMENT_CENTER
                    chipStrokeWidth = 1.dpFloat
                    setChipStrokeColorResource(R.color.orange2)
                    setTextAppearanceResource(R.style.filterTextStyle)
                    setChipBackgroundColorResource(R.color.white)
                }
                return chip
            }
        }
}