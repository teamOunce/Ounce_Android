package com.teamounce.ounce.settings

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teamounce.ounce.R

class SettingButtonAdapter (private val context:Context): RecyclerView.Adapter<SettingButtonViewHolder>(){
    var datas = mutableListOf<SettingButtonData>()

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): SettingButtonViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_setting_btn, p0,false)
        return SettingButtonViewHolder(view)
    }

    override fun onBindViewHolder(p0: SettingButtonViewHolder, p1: Int) {
        p0.bind(datas[p1])
    }

    override fun getItemCount() = datas.size
}