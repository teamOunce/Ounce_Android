package com.teamounce.ounce.settings

import android.app.ProgressDialog.show
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.teamounce.ounce.R
import com.teamounce.ounce.login.ui.RegisterFragment
import com.teamounce.ounce.login.ui.SignUpActivity
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

        //추가하기 눌렀을 때, 고양이 등록 페이지로 이동
        btn_catcare_add.setOnClickListener {
            val intent = Intent(this, RegisterFragment::class.java)
            startActivity(intent)
        }

        btn_setting_back.setOnClickListener{
            val intent = Intent(this,SettingsActivity::class.java )
            startActivity(intent)
        }

        //고양이 최대 4마리까지만 등록 가능
        if(datas.size < MAX_CAT_RESISTERED){
            val intent = Intent(this,SignUpActivity::class.java )
            startActivity(intent)
        }else{
            Toast.makeText(this, "고양이는 최대 4마리까지 등록 가능합니다.", Toast.LENGTH_SHORT)
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

    companion object {
        private const val MAX_CAT_RESISTERED = 4
    }

}