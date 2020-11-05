package com.teamounce.ounce

import android.app.Application
import android.util.Log
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.util.Utility
import com.teamounce.ounce.singleton.KeyPref

class OunceApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        KeyPref.init(this)
        KakaoSdk.init(this, KeyPref.getKey())
    }
}