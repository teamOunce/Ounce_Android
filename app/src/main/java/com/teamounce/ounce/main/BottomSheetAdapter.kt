package com.teamounce.ounce.main

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teamounce.ounce.R
import com.teamounce.ounce.settings.ui.SettingCareCatData

class BottomSheetAdapter(private val context : Context) :

    RecyclerView.Adapter<BottomSheetViewHolder>() {

    private var datas = mutableListOf<SettingCareCatData>()

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BottomSheetViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_bottom_main,parent,false)
        return BottomSheetViewHolder(view)
    }

    override fun onBindViewHolder(holder: BottomSheetViewHolder, position: Int) {
        holder.bind(datas[position])
    }
}







