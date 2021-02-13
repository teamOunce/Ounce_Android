package com.teamounce.ounce.feed.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.teamounce.ounce.R
import com.teamounce.ounce.databinding.ItemDetailTagBinding

class ReviewTagAdapter : RecyclerView.Adapter<ReviewTagAdapter.ResultTagViewHolder>() {
    private var tagList: List<String> = listOf()

    class ResultTagViewHolder(private val binding: ItemDetailTagBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(tag: String) { binding.tag = tag }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultTagViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemDetailTagBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_detail_tag, parent, false)
        return ResultTagViewHolder(binding)
    }

    override fun onBindViewHolder(holderResult: ResultTagViewHolder, position: Int) {
        holderResult.bind(tagList[position])
    }

    override fun getItemCount() = tagList.size

    fun replaceList(newTagList: List<String>) {
        tagList = newTagList
        notifyDataSetChanged()
    }
}