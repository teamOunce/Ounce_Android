package com.teamounce.ounce.feed.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.teamounce.ounce.R
import com.teamounce.ounce.databinding.ItemTagInfoBinding
import com.teamounce.ounce.review.model.ResponseTags

class TagAdapter(
    private val listener: DeleteClickListener
) : ListAdapter<ResponseTags.Data, TagAdapter.TagViewHolder>(TagDiffUtil()) {
    inner class TagViewHolder(private val binding: ItemTagInfoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(tag: ResponseTags.Data) {
            binding.tag = tag
            binding.imgTagDelete.setOnClickListener { listener.setOnClickListener(tag.tagIndex) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemTagInfoBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_tag_info, parent, false)
        return TagViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TagViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class TagDiffUtil : DiffUtil.ItemCallback<ResponseTags.Data>() {
        override fun areItemsTheSame(
            oldItem: ResponseTags.Data,
            newItem: ResponseTags.Data
        ): Boolean =
            oldItem.tagIndex == newItem.tagIndex

        override fun areContentsTheSame(
            oldItem: ResponseTags.Data,
            newItem: ResponseTags.Data
        ): Boolean =
            oldItem == newItem
    }

    interface DeleteClickListener {
        fun setOnClickListener(tagIndex: Int)
    }
}