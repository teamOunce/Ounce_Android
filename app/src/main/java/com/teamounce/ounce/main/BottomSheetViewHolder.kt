package com.teamounce.ounce.main

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.teamounce.ounce.R
import com.teamounce.ounce.settings.ui.SettingCareCatData
import kotlinx.android.synthetic.main.item_bottom_main.view.*

class BottomSheetViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
    val catName = itemView.findViewById<TextView>(R.id.cat_name)
    val catDday = itemView.findViewById<TextView>(R.id.cat_dday)


    fun bind(settingCareCatData: SettingCareCatData) {
        catName.text = settingCareCatData.setting_catname
        catDday.text = settingCareCatData.setting_catdday

    }

}
