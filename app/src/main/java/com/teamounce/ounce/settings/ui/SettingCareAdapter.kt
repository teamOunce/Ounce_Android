package com.teamounce.ounce.settings.ui


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.teamounce.ounce.R
import com.teamounce.ounce.main.BottomSheetData
import com.teamounce.ounce.main.BottomSheetViewHolder
import com.teamounce.ounce.settings.SettingCustomDialog
import com.teamounce.ounce.settings.SettingCustomDialogBuilder
import com.teamounce.ounce.settings.SettingCustomDialogListener
import com.teamounce.ounce.settings.SettingsCareActivity
import kotlinx.android.synthetic.main.item_setting_catcare.view.*

class SettingCareAdapter (private val context : Context) :

    RecyclerView.Adapter<SettingCareViewHolder>() {

    var datas = mutableListOf<SettingCareCatData>()
    private var limit = 4
    private val fragmentManager = (context as SettingsCareActivity).supportFragmentManager

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

        holder.itemView.setting_catdlt.setOnClickListener {
            if (datas.size == 1){
                val dialog = SettingCustomDialogBuilder()
                    .setTitle("정말 지우시겠어요?")
                    .setSubTitle("유일한 고양이에요.\n저장했던 기록들도 함께 사라져요.")
                    .setPositiveButton("네")
                    .setNegativeButton("잘못 눌렀어요")
                    .setButtonClickListener(object : SettingCustomDialogListener{
                        override fun onClickPositiveButton() {
                            Toast.makeText(context,"고양이를 삭제했습니다.",Toast.LENGTH_SHORT).show()
                        }

                        override fun onClickNegativeButton() {
                            Toast.makeText(context,"",Toast.LENGTH_SHORT).show()
                        }
                    })
                    .create()

                dialog.show(fragmentManager, dialog.tag)
            } else {
                val dialog = SettingCustomDialogBuilder()
                    .setTitle("정말 지우시겠어요?")
                    .setSubTitle("저장했던 기록들도 함께 사라져요.")
                    .setPositiveButton("네")
                    .setNegativeButton("잘못 눌렀어요")
                    .setButtonClickListener(object : SettingCustomDialogListener{
                        override fun onClickPositiveButton() {
                            Toast.makeText(context,"고양이를 삭제했습니다.",Toast.LENGTH_SHORT).show()
                        }

                        override fun onClickNegativeButton() {
                            Toast.makeText(context,"",Toast.LENGTH_SHORT).show()
                        }
                    })
                    .create()

                dialog.show(fragmentManager, dialog.tag)
            }
       }
    }


}
