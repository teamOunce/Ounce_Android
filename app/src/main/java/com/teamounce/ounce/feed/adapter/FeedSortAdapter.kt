package com.teamounce.ounce.feed.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teamounce.ounce.data.enum.EnumFeedSort
import com.teamounce.ounce.databinding.ItemFeedSortBinding

class FeedSortAdapter :
    RecyclerView.Adapter<FeedSortAdapter.FeedSortViewHolder>() {

    var sortStatus = listOf<String>()

    var checkedPosition = 0

    private var listener: ItemListener? = null

    interface ItemListener {
        fun clickSortStatus(status: EnumFeedSort)
    }

    fun setClickItemListener(l: ItemListener) {
        listener = l
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedSortViewHolder {
        val binding =
            ItemFeedSortBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FeedSortViewHolder(binding)
    }

    override fun getItemCount(): Int = sortStatus.size

    override fun onBindViewHolder(holder: FeedSortViewHolder, position: Int) {
        holder.onBind(sortStatus[position])
    }


    inner class FeedSortViewHolder(val binding: ItemFeedSortBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: String) {

            binding.radioCheck.isChecked =
                checkedPosition != -1 && checkedPosition == adapterPosition

            binding.txtSortStatus.text = data
            binding.lineDivider.visibility =
                if (adapterPosition == sortStatus.size - 1) View.GONE else View.VISIBLE

            binding.layoutSortStatus.setOnClickListener {


                if (checkedPosition != adapterPosition) {
                    checkedPosition = adapterPosition
                }

                listener?.clickSortStatus(
                    when (adapterPosition) {
                        0 -> EnumFeedSort.WRITE
                        1 -> EnumFeedSort.PREFERENCE_HIGH
                        2 -> EnumFeedSort.PREFERENCE_LOW
                        else -> EnumFeedSort.WRITE
                    }
                )
            }
        }
    }
}