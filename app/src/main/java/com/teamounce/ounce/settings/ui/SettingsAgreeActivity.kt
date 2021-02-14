package com.teamounce.ounce.settings.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.teamounce.ounce.R
import com.teamounce.ounce.util.StatusBarUtil
import kotlinx.android.synthetic.main.activity_settings_agree.*

class SettingsAgreeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings_agree)
        StatusBarUtil.setStatusBar(this)
        btn_setting_back.setOnClickListener { finish() }
    }
}