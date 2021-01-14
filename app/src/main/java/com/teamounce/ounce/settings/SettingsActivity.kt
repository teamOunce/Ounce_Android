package com.teamounce.ounce.settings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.teamounce.ounce.R
import com.teamounce.ounce.util.VerticalItemDecoration
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {
    lateinit var settingButtonAdapter: SettingButtonAdapter
    val datas = mutableListOf<SettingButtonData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        settingButton()
    }

    fun settingButton(){
        settingButtonAdapter = SettingButtonAdapter(this)
        recyclerview_setting_buttons.adapter = settingButtonAdapter
        recyclerview_setting_buttons.addItemDecoration(VerticalItemDecoration(12))
        loadSettingButton()
    }


    fun loadSettingButton(){
        datas.apply {
            add(
                SettingButtonData(
                    "고양이 관리"
                )
            )
            add(
                SettingButtonData(
                    "공지사항"
                )
            )

            add(
                SettingButtonData(
                    "이용약관"
                )
            )

            add(
                SettingButtonData(
                    "문의하기"
                )
            )

            add(
                SettingButtonData(
                    "기록 백업 및 복원"
                )
            )

            settingButtonAdapter.datas = datas
            settingButtonAdapter.notifyDataSetChanged()
        }
    }
}