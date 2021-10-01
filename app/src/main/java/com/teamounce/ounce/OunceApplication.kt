package com.teamounce.ounce

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.multidex.MultiDex
import com.kakao.sdk.common.KakaoSdk
import com.teamounce.ounce.data.local.singleton.OunceLocalRepository
import com.teamounce.ounce.util.PixelRatio
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class OunceApplication : Application() {
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        // 다크모드 설정 해제
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        initPixelUtil()
        OunceLocalRepository.init(this)
        KakaoSdk.init(this, OunceLocalRepository.getKey(), loggingEnabled = true)
    }

    private fun initPixelUtil() {
        pixelRatio = PixelRatio(this)
    }

    companion object {
        lateinit var pixelRatio: PixelRatio
    }
}