package com.teamounce.ounce.settings

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.teamounce.ounce.R

class SettingButtonViewHolder (itemView:View): RecyclerView.ViewHolder(itemView){
    val buttonName = itemView.findViewById<TextView>(R.id.textview_button_name)

    fun bind(settingButtonData:SettingButtonData){
        buttonName.text = settingButtonData.buttonName
    }
}