package com.teamounce.ounce.feed.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.teamounce.ounce.R
import com.teamounce.ounce.databinding.ItemFeedListBinding
import com.teamounce.ounce.feed.model.ResponseFeedReviewData
import com.teamounce.ounce.feed.ui.FoodDetailActivity
import com.teamounce.ounce.review.ui.ReviewModifyActivity
import com.teamounce.ounce.util.ChipClient
import com.teamounce.ounce.util.SmallChipFactory
import com.teamounce.ounce.util.dp
import com.teamounce.ounce.util.dpFloat

class FeedListAdapter : RecyclerView.Adapter<FeedListAdapter.FeedListViewHolder>() {

    var feedList = listOf<ResponseFeedReviewData.Data>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedListViewHolder {
        val binding =
            ItemFeedListBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return FeedListViewHolder(binding, parent.context)
    }

    override fun getItemCount(): Int = feedList.size

    override fun onBindViewHolder(holder: FeedListViewHolder, position: Int) {
        holder.onBind(feedList[position])
    }

    inner class FeedListViewHolder(val binding: ItemFeedListBinding, val context: Context) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(data: ResponseFeedReviewData.Data) {
            binding.data = data

            binding.feedChipGroup.removeAllViews()

            if (data.tag1.isNotBlank()) {
                val chip = setChip(data.tag1)
                binding.feedChipGroup.addView(chip)
            }

            if (data.tag2.isNotBlank()) {
                val chip = setChip(data.tag2)
                binding.feedChipGroup.addView(chip)
            }

            if (data.tag3.isNotBlank()) {
                val chip = setChip(data.tag3)
                binding.feedChipGroup.addView(chip)
            }

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, FoodDetailActivity::class.java)
                intent.putExtra("reviewIndex", data.reviewIndex)
                itemView.context.startActivity(intent)
            }
        }

        @SuppressLint("SetTextI18n")
        private fun setChip(tag: String): Chip {
            return ChipClient.create(LayoutInflater.from(context), SmallChipFactory()).apply {
                text = "#${tag}"
                isChecked = true
                isClickable = false
                isFocusable = false
                isEnabled = false
            }
        }
    }
}