package com.teamounce.ounce.login.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.teamounce.ounce.R
import com.teamounce.ounce.databinding.ItemOnboardingBinding
import com.teamounce.ounce.login.model.OnBoardingData

class OnBoardingAdapter : RecyclerView.Adapter<OnBoardingAdapter.OnBoardingViewHolder>() {
    private val onBoardingList = mutableListOf<OnBoardingData>()

    class OnBoardingViewHolder(private val binding: ItemOnboardingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(onBoardingData: OnBoardingData) {
            binding.onBoardingData = onBoardingData
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnBoardingViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemOnboardingBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_onboarding, parent, false)
        return OnBoardingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OnBoardingViewHolder, position: Int) {
        holder.bind(onBoardingList[position])
    }

    override fun getItemCount() = onBoardingList.size

    fun replaceList(newOnBoardingList: List<OnBoardingData>) {
        onBoardingList.clear()
        onBoardingList.addAll(newOnBoardingList)
        notifyDataSetChanged()
    }
}