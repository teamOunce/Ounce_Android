package com.teamounce.ounce.settings.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.teamounce.ounce.R
import com.teamounce.ounce.settings.model.SettingButtonData

class SettingButtonViewHolder (itemView:View): RecyclerView.ViewHolder(itemView){
    val buttonName = itemView.findViewById<TextView>(R.id.textview_button_name)
    val buttonImage = itemView.findViewById<ImageView>(R.id.imageview_button)

    fun bind(settingButtonData: SettingButtonData){
        buttonName.text = settingButtonData.buttonName
        Glide.with(itemView).load(settingButtonData.buttonImage).into(buttonImage)

    }
}