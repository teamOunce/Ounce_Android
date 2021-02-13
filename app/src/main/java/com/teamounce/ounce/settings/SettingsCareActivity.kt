package com.teamounce.ounce.settings

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.teamounce.ounce.R
import com.teamounce.ounce.main.BottomSheetProfileData
import com.teamounce.ounce.settings.ui.SettingCareAdapter
import com.teamounce.ounce.util.SharedPreferences
import kotlinx.android.synthetic.main.activity_settings_care.*

class SettingsCareActivity : AppCompatActivity() {
    lateinit var settingcareAdapter: SettingCareAdapter
    private var catList = mutableListOf<BottomSheetProfileData>()
    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings_care)
        initSettingCareRecyclerView()
        loadDatas()
        goToBack()
        registerCat()
    }


    fun goToBack() {
        btn_setting_back.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }
    }

    fun registerCat() {
        // 추가하기 눌렀을 때, 고양이 등록 페이지로 이동
        btn_catcare_add.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
            //고양이 최대 4마리까지만 등록 가능
            if (catList.size >= MAX_CAT_RESISTERED) {
                Toast.makeText(this, "고양이는 최대 4마리까지 등록 가능합니다.", Toast.LENGTH_SHORT)
            }
        }
    }

    fun initSettingCareRecyclerView() {
        settingcareAdapter = SettingCareAdapter(this)
        settings_recyclerview_list.adapter = settingcareAdapter
    }

    fun loadDatas() {
        prefs = SharedPreferences(this)
        catList = prefs.getCatList()
        settingcareAdapter.datas = catList
        settingcareAdapter.notifyDataSetChanged()
    }

    companion object {
        private const val MAX_CAT_RESISTERED = 4
    }
}