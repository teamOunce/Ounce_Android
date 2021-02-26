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
                    val intent = Intent(Intent.ACTION_SEND)
                    intent.type ="plain/text"
                    intent.putExtra(Intent.EXTRA_EMAIL, arrayOf("ounceforyou@gmail.com"))
                    intent.putExtra(Intent.EXTRA_SUBJECT, "ounceforyou 문의사항")
                    intent.putExtra(Intent.EXTRA_TEXT,
                        "- 기록하고 싶은 제품 제안 \n" +
                        "- 사용 중 불편함 \n"+
                        "- 건의사항 \n"+
                        "* 세세하세 적어주시면 빠른 처리가 가능합니다.:) \n"+
                        "\n" +
                        "문의 내용: \n"
                    )
                    intent.type = "message/rfc822"
                    context.startActivity(intent)

                }

                3->{
                    Toast.makeText(context,"준비중인 기능입니다.",Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    override fun getItemCount() = datas.size
}