package com.teamounce.ounce.settings.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.teamounce.ounce.R
import com.teamounce.ounce.databinding.ActivitySettingsCareBinding
import com.teamounce.ounce.main.BottomSheetProfileData
import com.teamounce.ounce.settings.adapter.SettingCareAdapter
import com.teamounce.ounce.settings.viewmodel.CatProfileViewModel
import com.teamounce.ounce.util.SharedPreferences
import com.teamounce.ounce.util.StatusBarUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsCareActivity : AppCompatActivity() {
    lateinit var settingcareAdapter: SettingCareAdapter
    private var catList = mutableListOf<BottomSheetProfileData>()
    private lateinit var prefs: SharedPreferences
    private val catProfileViewModel by viewModels<CatProfileViewModel>()
    private lateinit var binding: ActivitySettingsCareBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsCareBinding.inflate(layoutInflater)
        setContentView(binding.root)
        StatusBarUtil.setStatusBar(this)
        setUIListener()
        initSettingCareRecyclerView()
        loadDatas()
        catProfileViewModel.catProfileList.observe(this) {
            settingcareAdapter.datas = it.toMutableList()
            prefs.setCatList(it.toMutableList())
            settingcareAdapter.notifyDataSetChanged()
        }
        catProfileViewModel.errorMessage.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setUIListener() {
        binding.btnSettingBack.setOnClickListener { finish() }
        binding.btnSettingBack.setOnClickListener {
            if (catList.size >= MAX_CAT_RESISTERED) {
                Toast.makeText(this, "고양이는 최대 4마리까지 등록 가능합니다.", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this, CatRegisterActivity::class.java)
                startActivityForResult(intent, CAT_REGISTER)
            }
        }
    }

    private fun initSettingCareRecyclerView() {
        prefs = SharedPreferences(this)
        settingcareAdapter = SettingCareAdapter(this, provideDeleteButtonClickListener())
        binding.settingsRecyclerviewList.adapter = settingcareAdapter
    }

    private fun loadDatas() { catProfileViewModel.getCatProfiles() }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CAT_REGISTER) {
            if (resultCode == REGISTER_SUCCESS) { catProfileViewModel.getCatProfiles() }
        }
    }

    private fun provideDeleteButtonClickListener(): SettingCareAdapter.CatDeleteButton {
        return object : SettingCareAdapter.CatDeleteButton {
            override fun OnClickListener(position: Int) {
                catProfileViewModel.getCatProfiles()
                settingcareAdapter.datas.removeAt(position)
                prefs.setCatList(settingcareAdapter.datas)
                settingcareAdapter.notifyDataSetChanged()
            }
        }
    }

    companion object {
        private const val MAX_CAT_RESISTERED = 4
        const val CAT_REGISTER = 9000
        const val REGISTER_SUCCESS = 8765
    }
}