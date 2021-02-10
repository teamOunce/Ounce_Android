package com.teamounce.ounce.settings

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.teamounce.ounce.R

class SettingsOpensourceViewHolder (itemView: View): RecyclerView.ViewHolder(itemView) {
    val openSourceName = itemView.findViewById<TextView>(R.id.setting_opensource_name)
    val openSourceLink = itemView.findViewById<TextView>(R.id.setting_opensource_link)
    val openSourceCopyright = itemView.findViewById<TextView>(R.id.setting_opensource_copyright)

    fun bind(settingOpensourceData:SettingsOpensourceData){
        openSourceName.text = settingOpensourceData.setting_opensource_name
        openSourceLink.text = settingOpensourceData.setting_opensource_link
        openSourceCopyright.text= settingOpensourceData.setting_opensource_copyright
    }


}