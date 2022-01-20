package com.teamounce.ounce.settings.adapter

import androidx.recyclerview.widget.RecyclerView
import com.teamounce.ounce.databinding.ItemSettingCatcareBinding
import com.teamounce.ounce.main.BottomSheetProfileData

class SettingCareViewHolder(
    val binding: ItemSettingCatcareBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(bottomSheetProfileData: BottomSheetProfileData) {
        binding.settingCatname.text = bottomSheetProfileData.catName
        binding.settingCatdday.text = "만난지 ${bottomSheetProfileData.fromMeet + 1} 일 째"
    }
}





