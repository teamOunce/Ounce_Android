package com.teamounce.ounce.settings.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teamounce.ounce.R
import com.teamounce.ounce.settings.model.SettingsOpensourceData

class SettingsOpensourceAdapter(private val context: Context) :
    RecyclerView.Adapter<SettingsOpensourceViewHolder>() {
    var datas = mutableListOf<SettingsOpensourceData>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SettingsOpensourceViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_setting_opensource, parent ,false)
        return SettingsOpensourceViewHolder(view)
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(holder: SettingsOpensourceViewHolder, position: Int) {
        holder.bind(datas[position])

    }
}