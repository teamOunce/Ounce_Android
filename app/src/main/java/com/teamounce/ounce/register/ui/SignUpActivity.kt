package com.teamounce.ounce.register.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.teamounce.ounce.R
import com.teamounce.ounce.util.StatusBarUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        StatusBarUtil.setStatusBar(this)
    }
}