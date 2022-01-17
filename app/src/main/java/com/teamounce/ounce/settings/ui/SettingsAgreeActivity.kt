package com.teamounce.ounce.settings.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.teamounce.ounce.databinding.ActivitySettingsAgreeBinding
import com.teamounce.ounce.util.StatusBarUtil

class SettingsAgreeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySettingsAgreeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        StatusBarUtil.setStatusBar(this)
        binding.btnSettingBack.setOnClickListener { finish() }
    }
}