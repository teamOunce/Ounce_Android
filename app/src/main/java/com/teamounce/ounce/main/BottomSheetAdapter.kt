package com.teamounce.ounce.main

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teamounce.ounce.R

class BottomSheetAdapter(private val context : Context) :

    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var datas = mutableListOf<BottomSheetData>()

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BottomSheetViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.fragment_bottom_main,parent,false)
        return BottomSheetViewHolder(view)
    }

    fun onBindViewHolder(
        holder: BottomSheetViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        holder.bind(datas[position])

    }

     class ViewHolder (parent : ViewGroup, viewType: Int) {
        fun bind(bottomSheetData: BottomSheetData) {

        }


    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }


}







