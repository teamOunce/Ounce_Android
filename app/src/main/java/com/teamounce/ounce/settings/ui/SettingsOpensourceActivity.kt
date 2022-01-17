package com.teamounce.ounce.settings.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.teamounce.ounce.R
import com.teamounce.ounce.databinding.ActivitySettingsOpensourceBinding
import com.teamounce.ounce.settings.adapter.SettingsOpensourceAdapter
import com.teamounce.ounce.settings.model.SettingsOpensourceData
import com.teamounce.ounce.util.StatusBarUtil
import com.teamounce.ounce.util.VerticalItemDecoration

class SettingsOpensourceActivity : AppCompatActivity() {
    private lateinit var settingsOpenSourceAdapter: SettingsOpensourceAdapter
    private lateinit var binding: ActivitySettingsOpensourceBinding
    private var datas = mutableListOf<SettingsOpensourceData>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsOpensourceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        StatusBarUtil.setStatusBar(this)
        setOpenSourceRecyclerItem()
        loadDatas()

        binding.btnOpensourceBack.setOnClickListener { finish() }
    }

    fun setOpenSourceRecyclerItem() {
        settingsOpenSourceAdapter = SettingsOpensourceAdapter(this)
        binding.settingOpensourceRecyclerview.adapter = settingsOpenSourceAdapter
        binding.settingOpensourceRecyclerview.addItemDecoration(VerticalItemDecoration(12))
    }

    fun loadDatas() {
        datas.apply {
            add(
                SettingsOpensourceData(
                    "Dots Indicator",
                    "https://github.com/tommybuonomo/dotsindicator",
                    "Copyright 2016 Tommy Buonomo."
                )
            )
            add(
                SettingsOpensourceData(
                    "Android Constraint Layout Library",
                    "https://developer.android.com/reference/android/support/constraint/packages",
                    "Copyright 2017 The Android Open Source Project."
                )
            )
            add(
                SettingsOpensourceData(
                    "Android Databinding Library",
                    "https://developer.android.com/reference/android/databinding/packages",
                    "Copyright 2018 The Android Open Source Project."
                )
            )
            add(
                SettingsOpensourceData(
                    "AndroidX Library",
                    "https://developer.android.com/reference/androidx/packages",
                    "Copyright 2018 The Android Open Source Project."
                )
            )
            add(
                SettingsOpensourceData(
                    "Android KTX",
                    "https://github.com/android/android-ktx",
                    "Copyright 2018 The Android Open Source Project."
                )
            )
            add(
                SettingsOpensourceData(
                    "Android Material Components",
                    "https://developer.android.com/reference/com/google/android/material/packages",
                    "Copyright 2018 The Android Open Source Project"
                )
            )
            add(
                SettingsOpensourceData(
                    "Annotations for JVM-based languages",
                    "https://github.com/JetBrains/java-annotations",
                    "Copyright 2000-2016 JetBrains s.r.o."
                )
            )
            add(
                SettingsOpensourceData(
                    "CircleImageView",
                    "https://github.com/hdodenhof/CircleImageView",
                    "Copyright 2014 - 2020 Henning Dodenhof"
                )
            )
            add(
                SettingsOpensourceData(
                    "Kakao SDK",
                    "https://developers.kakao.com/sdk/reference/android-rx/release/kakao-android-sdk-rx/com.kakao.sdk.auth/index.html",
                    "Copyright 2014-2020 Kakao Corp."
                )
            )
            add(
                SettingsOpensourceData(
                    "Google Gson",
                    "https://github.com/google/gson",
                    "Copyright 2008 Google Inc."
                )
            )
            add(
                SettingsOpensourceData(
                    "Kotlin",
                    "https://github.com/jetbrains/kotlin",
                    "Copyright 2010-2020 JetBrains s.r.o."
                )
            )
            add(
                SettingsOpensourceData(
                    "Square OkHttp",
                    "https://github.com/square/okhttp",
                    "Copyright 2014 Square, Inc."
                )
            )
            add(
                SettingsOpensourceData(
                    "Square Retrofit",
                    "https://github.com/square/retrofit",
                    "Copyright 2013 Square, Inc."
                )
            )
            add(
                SettingsOpensourceData(
                    "Glide",
                    "https://github.com/bumptech/glide",
                    "Copyright 2014 Google, Inc."
                )
            )
            add(
                SettingsOpensourceData(
                    "TedPermission",
                    "https://github.com/ParkSangGwon/TedPermission",
                    "Copyright 2017 Ted Park."
                )
            )
            add(
                SettingsOpensourceData(
                    "Lottie Android",
                    "https://github.com/airbnb/lottie-android/",
                    "Copyright 2018 Airbnb, Inc."
                )
            )
            add(
                SettingsOpensourceData(
                    "Firebase SDK",
                    "https://github.com/firebase/firebase-android-sdk/blob/master/LICENSE",
                    "Copyright 2012 The Android Open Source Project."
                )
            )
            add(
                SettingsOpensourceData(
                    "TedKeyboardObserver",
                    "https://github.com/ParkSangGwon/TedKeyboardObserver",
                    "Copyright 2019 Ted Park."
                )
            )
        }
        settingsOpenSourceAdapter.datas = datas
        settingsOpenSourceAdapter.notifyDataSetChanged()
    }
}