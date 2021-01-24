package com.teamounce.ounce.settings

import android.app.ProgressDialog.show
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.teamounce.ounce.R
import com.teamounce.ounce.settings.ui.SettingCareAdapter
import com.teamounce.ounce.settings.ui.SettingCareCatData
import kotlinx.android.synthetic.main.activity_settings_care.*
import kotlinx.android.synthetic.main.bottom_sheet_main.*
import kotlinx.android.synthetic.main.item_setting_catcare.*

class  SettingsCareActivity : AppCompatActivity() {
    lateinit var settingcareAdapter: SettingCareAdapter
    private var datas = mutableListOf<SettingCareCatData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings_care)

        initSettingCareRecyclerView()
        loadDatas()

        btn_setting_back.setOnClickListener{
            val intent = Intent(this,SettingsActivity::class.java )
            startActivity(intent)
        }



    }

    fun initSettingCareRecyclerView() {
        settingcareAdapter = SettingCareAdapter(this)
        settings_recyclerview_list.adapter = settingcareAdapter
    }

    fun loadDatas(){
        datas.apply {
            add(
                SettingCareCatData(
                    "이겨울",
                    "만난지 1030일째"
                )
            )
            add(
                SettingCareCatData(
                    "주예",
                    "만난지 63일째"
                )
            )
            add(
                SettingCareCatData(
                    "애옹",
                    "만난지 3970일째"
                )
            )
            add (
                SettingCareCatData(
                    "예인",
                    "만난지 300일"
                )
            )

        }
        settingcareAdapter.datas = datas
        settingcareAdapter.notifyDataSetChanged()
    }


}