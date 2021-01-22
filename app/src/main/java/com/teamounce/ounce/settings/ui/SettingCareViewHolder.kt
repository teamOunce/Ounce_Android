package com.teamounce.ounce.settings.ui

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.teamounce.ounce.R

class SettingCareViewHolder (itemView: View):RecyclerView.ViewHolder(itemView) {
    val settingCatname = itemView.findViewById<TextView>(R.id.setting_catname)
    val settingCatDday = itemView.findViewById<TextView>(R.id.setting_catdday)


    fun bind(settingCareCatData: SettingCareCatData){
        settingCatname.text = settingCareCatData.setting_catname
        settingCatDday.text = settingCareCatData.setting_catdday
    }
}





