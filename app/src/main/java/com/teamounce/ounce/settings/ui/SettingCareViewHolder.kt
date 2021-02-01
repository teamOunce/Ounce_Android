package com.teamounce.ounce.settings.ui

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.teamounce.ounce.R
import com.teamounce.ounce.main.BottomSheetProfileData

class SettingCareViewHolder (itemView: View):RecyclerView.ViewHolder(itemView) {
    val settingCatname = itemView.findViewById<TextView>(R.id.setting_catname)
    val settingCatDday = itemView.findViewById<TextView>(R.id.setting_catdday)


    fun bind(bottomSheetProfileData: BottomSheetProfileData){
        settingCatname.text = bottomSheetProfileData.catName
        settingCatDday.text = bottomSheetProfileData.fromMeet.toString()
    }
}





