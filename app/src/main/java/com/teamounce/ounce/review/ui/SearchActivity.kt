package com.teamounce.ounce.review.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.databinding.DataBindingUtil
import com.teamounce.ounce.R
import com.teamounce.ounce.databinding.ActivitySearchBinding

class SearchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivitySearchBinding = DataBindingUtil.setContentView(this, R.layout.activity_search)
        setContentView(R.layout.activity_search)
    }

}