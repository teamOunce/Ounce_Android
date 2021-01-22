package com.teamounce.ounce.settings

import android.content.Context
import android.content.pm.PackageInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.common.wrappers.Wrappers.packageManager
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
        ounceversion(this)
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
                    R.drawable.ic_cat,
                    "고양이 관리"
                )
            )
            add(
                SettingButtonData(
                    R.drawable.ic_notice,
                    "공지사항"
                )
            )

            add(
                SettingButtonData(
                    R.drawable.ic_tos,
                    "이용약관"
                )
            )

            add(
                SettingButtonData(
                    R.drawable.ic__question,
                    "문의하기"
                )
            )

            add(
                SettingButtonData(
                    R.drawable.ic_backup,
                    "기록 백업 및 복원"
                )
            )

            settingButtonAdapter.datas = datas
            settingButtonAdapter.notifyDataSetChanged()
        }
    }
    fun ounceversion(context:Context){
        val info:PackageInfo = context.packageManager.getPackageInfo(context.packageName,0)
        val version = info.versionName

        txt_ounceversion.text = version
    }
}