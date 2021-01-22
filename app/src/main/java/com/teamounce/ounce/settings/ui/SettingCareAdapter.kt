package com.teamounce.ounce.settings.ui


import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teamounce.ounce.R
import com.teamounce.ounce.main.BottomSheetData
import com.teamounce.ounce.main.BottomSheetViewHolder

class SettingCareAdapter (private val context : Context) :

    RecyclerView.Adapter<SettingCareViewHolder>() {

    var datas = mutableListOf<SettingCareCatData>()

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SettingCareViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_setting_catcare,parent,false)
        return SettingCareViewHolder(view)
    }

    override fun onBindViewHolder(holder: SettingCareViewHolder, position: Int) {
        holder.bind(datas[position])
    }
}
