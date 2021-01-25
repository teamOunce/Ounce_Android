package com.teamounce.ounce

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import com.teamounce.ounce.singleton.KeyPref
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class OunceApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        KeyPref.init(this)
        KakaoSdk.init(this, KeyPref.getKey())
    }
}