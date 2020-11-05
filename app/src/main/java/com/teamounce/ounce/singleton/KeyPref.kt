package com.teamounce.ounce.singleton

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

object KeyPref {
    private val PREF_KEY = "haskhey"
    private val NATIVE_APP_KEY = "appkey"
    private val keyGenParameterSpec = MasterKeys.AES256_GCM_SPEC
    private val masterKeyAlias = MasterKeys.getOrCreate(keyGenParameterSpec)
    private lateinit var sharedPref: SharedPreferences

    fun init(context: Context) {
        sharedPref = EncryptedSharedPreferences.create(
            PREF_KEY,
            masterKeyAlias,
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
        sharedPref
            .edit()
            .putString(NATIVE_APP_KEY, "7171285de2b4a7896ab049321ffc8ed6")
            .apply()
    }

    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = edit()
        operation(editor)
        editor.apply()
    }

    fun getKey(): String {
        return sharedPref.getString(NATIVE_APP_KEY, "")!!
    }
}