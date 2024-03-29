package com.teamounce.ounce.settings.adapter

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.teamounce.ounce.R
import com.teamounce.ounce.settings.model.SettingButtonData
import com.teamounce.ounce.settings.ui.SettingsCareActivity

class SettingButtonAdapter (private val context:Context): RecyclerView.Adapter<SettingButtonViewHolder>(){
    var datas = mutableListOf<SettingButtonData>()
    val uriInsta : Uri = Uri.parse("https://instagram.com/ounceforyou?igshid=1bypchn4t7xme")
    val forApp = Intent(Intent.ACTION_VIEW , uriInsta)



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
                1 -> {
                    try {
                        context.startActivity(forApp)
                    }catch (e:ActivityNotFoundException){
                        startActivity ( context ,forApp , null )
                    }
                }
                2 -> {
                    context.startActivity(Intent(Intent.ACTION_VIEW).apply {
                        data = Uri.parse("https://forms.gle/GL6r3kNrr32e5xFR6")
                    })

                }
                else -> {}
            }
        }

    }

    override fun getItemCount() = datas.size
}