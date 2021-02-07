package com.teamounce.ounce.feed.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.teamounce.ounce.R
import com.teamounce.ounce.databinding.ItemFeedListBinding
import com.teamounce.ounce.feed.model.ResponseFeedListData
import com.teamounce.ounce.util.dpFloat
import com.teamounce.ounce.util.pixel
import com.teamounce.ounce.util.pixelFloat

class FeedListAdapter : RecyclerView.Adapter<FeedListAdapter.FeedListViewHolder>() {

    var feedList = listOf<ResponseFeedListData>()

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

            fun onBind(data : ResponseFeedListData){
                binding.data = data
                val chip = Chip(itemView.context)
                chip.apply {
                    layoutDirection = View.LAYOUT_DIRECTION_LOCALE
                    text = "#테스트"
                    textAlignment = View.TEXT_ALIGNMENT_CENTER
                    chipStrokeWidth = 1.dpFloat
                    setChipStrokeColorResource(R.color.orange2)
                    setTextAppearanceResource(R.style.filterTextStyle)
                    setChipBackgroundColorResource(R.color.white)
                }
                binding.feedChipGroup.addView(chip)
            }
        }
}