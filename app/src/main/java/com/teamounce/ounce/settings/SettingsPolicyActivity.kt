package com.teamounce.ounce.settings

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.teamounce.ounce.R
import com.teamounce.ounce.util.StatusBarUtil
import kotlinx.android.synthetic.main.activity_settings_policy.*

class SettingsPolicyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings_policy)
        StatusBarUtil.setStatusBar(this)
        btn_policy_back.setOnClickListener { finish() }
    }
}