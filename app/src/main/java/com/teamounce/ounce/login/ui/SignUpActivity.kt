package com.teamounce.ounce.login.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.teamounce.ounce.R
import com.teamounce.ounce.util.TransparentStatusBarObject

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        TransparentStatusBarObject.setStatusBar(this)

    }
}