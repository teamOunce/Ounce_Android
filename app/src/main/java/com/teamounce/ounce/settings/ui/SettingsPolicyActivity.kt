package com.teamounce.ounce.settings.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.teamounce.ounce.databinding.ActivitySettingsPolicyBinding
import com.teamounce.ounce.util.StatusBarUtil

class SettingsPolicyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySettingsPolicyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        StatusBarUtil.setStatusBar(this)
        binding.btnPolicyBack.setOnClickListener { finish() }
    }
}