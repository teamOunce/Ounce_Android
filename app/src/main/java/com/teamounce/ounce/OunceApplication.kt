package com.teamounce.ounce

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import com.teamounce.ounce.singleton.KeyPref
import com.teamounce.ounce.util.PixelRatio
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class OunceApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initPixelUtil()
        KeyPref.init(this)
        KakaoSdk.init(this, KeyPref.getKey())
    }

    private fun initPixelUtil() {
        pixelRatio = PixelRatio(this)
    }

    companion object {
        lateinit var pixelRatio: PixelRatio
    }
}