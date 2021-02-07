package com.teamounce.ounce.feed.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.teamounce.ounce.R
import com.teamounce.ounce.databinding.ItemDetailTagBinding

class TagAdapter : RecyclerView.Adapter<TagAdapter.TagViewHolder>() {
    private var tagList: List<String> = listOf()

    class TagViewHolder(private val binding: ItemDetailTagBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(tag: String) {
            // TODO 이거 binding에 string 데이터 달아라 현우야
            binding.txtDetailTag.text = tag
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemDetailTagBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_detail_tag, parent, false)
        return TagViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TagViewHolder, position: Int) {
        holder.bind(tagList[position])
    }

    override fun getItemCount() = tagList.size

    fun replaceList(newTagList: List<String>) {
        tagList = newTagList
        notifyDataSetChanged()
    }
}