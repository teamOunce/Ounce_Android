package com.teamounce.ounce.settings

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.teamounce.ounce.R
import com.teamounce.ounce.settings.ui.SettingCareAdapter
import com.teamounce.ounce.settings.ui.SettingCareCatData
import com.teamounce.ounce.util.VerticalItemDecoration
import kotlinx.android.synthetic.main.activity_settings.*
import kotlinx.android.synthetic.main.activity_settings_agree.*
import kotlinx.android.synthetic.main.activity_settings_care.*
import kotlinx.android.synthetic.main.activity_settings_care.btn_setting_back
import kotlinx.android.synthetic.main.activity_settings_opensource.*

class SettingsOpensourceActivity : AppCompatActivity() {
    lateinit var settingsOpenSourceAdapter: SettingsOpensourceAdapter
    private var datas = mutableListOf<SettingsOpensourceData>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings_opensource)
        setOpenSourceRecyclerItem()
        loadDatas()

        btn_opensource_back.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }
    }
    fun setOpenSourceRecyclerItem() {
        settingsOpenSourceAdapter = SettingsOpensourceAdapter(this)
        setting_opensource_recyclerview.adapter = settingsOpenSourceAdapter
        setting_opensource_recyclerview.addItemDecoration(VerticalItemDecoration(12))
    }
    fun loadDatas(){
        datas.apply {
            add(
                SettingsOpensourceData(
                    "Glide",
                    "https://github.com/bumptech/glide",
                    "Copyright 2014 Google Inc."
                )
            )
            add(
                SettingsOpensourceData("OkHttp",
                    "https://square.github.io/okhttp",
                    "Copyright 2016 Square Inc."
                )
            )
            add(
                SettingsOpensourceData(
                    "Gson",
                    "https://github.com/google/gson",
                    "Copyright 2008 Google Inc."
                )
            )
            add (
                SettingsOpensourceData(
                    "OkHttp",
                    "https://square.github.io/okhttp",
                    "Copyright 2016 Square Inc."
                )
            )
            add (
                SettingsOpensourceData(
                    "ACRA",
                    "https://github.com/ACRA/acra",
                    "Copyright 2010 Emmanuel Astier & Kevin Gaudin"
                )
            )
            add (
                SettingsOpensourceData(
                    "ActionBarSherlock",
                    "https://square.github.io/okhttphttps://github.com/JakeWharton/ActionBarSherlock",
                    "Copyright 2012 Jake Wharton"
                )
            )
            add (
                SettingsOpensourceData(
                    "Alexel",
                    "https://github.com/thiagokimo/Alexei",
                    "Copyright 2011-2012 Thiago Rocha"
                )
            )
            add (
                SettingsOpensourceData(
                    "Flex",
                    "https://github.com/Cool",
                    "Copyright 2021 flex inc"
                )
            )
            add (
                SettingsOpensourceData(
                    "BottomItem",
                    "https://github.com/itdgodownlikethis",
                    "Copyright 2012 Jake Wharton"
                )
            )

        }
        settingsOpenSourceAdapter.datas = datas
        settingsOpenSourceAdapter.notifyDataSetChanged()
    }
}