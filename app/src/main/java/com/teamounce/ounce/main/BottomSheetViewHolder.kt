package com.teamounce.ounce.main

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.teamounce.ounce.R
import kotlinx.android.synthetic.main.fragment_bottom_main.view.*

class BottomSheetViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
    val cat_name = itemView.findViewById<TextView>(R.id.cat_name)
    val cat_dday = itemView.findViewById<TextView>(R.id.cat_dday)
    val cat_selected_btn = itemView.findViewById<TextView>(R.id.cat_select_btn)

    fun bind(bottomSheetData: BottomSheetData) {
    }

}
