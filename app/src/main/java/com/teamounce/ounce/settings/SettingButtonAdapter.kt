package com.teamounce.ounce.settings

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.teamounce.ounce.R

class SettingButtonAdapter (private val context:Context): RecyclerView.Adapter<SettingButtonViewHolder>(){
    var datas = mutableListOf<SettingButtonData>()

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): SettingButtonViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_setting_btn, p0,false)
        return SettingButtonViewHolder(view)
    }

    override fun onBindViewHolder(holder: SettingButtonViewHolder, position: Int) {
        holder.bind(datas[position])

        holder.itemView.setOnClickListener {
            when(position) {
                0 -> {
                    val intent = Intent(context, SettingsCareActivity::class.java)
                    context.startActivity(intent)
                }
                2 -> {
                    val intent = Intent(context, SettingsAgreeActivity::class.java)
                    context.startActivity(intent)
                }
                4->{
                    Toast.makeText(context,"준비중인 기능입니다.",Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    override fun getItemCount() = datas.size
}