package com.teamounce.ounce.register.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.teamounce.ounce.R
import com.teamounce.ounce.util.StatusBarUtil

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        StatusBarUtil.setStatusBar(this)
    }
}