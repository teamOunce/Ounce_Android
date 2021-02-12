package com.teamounce.ounce.feed.ui

import android.os.Bundle
import com.teamounce.ounce.R
import com.teamounce.ounce.base.BindingActivity
import com.teamounce.ounce.databinding.ActivityTagBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TagActivity : BindingActivity<ActivityTagBinding>(R.layout.activity_tag) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}