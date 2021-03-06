package com.teamounce.ounce.data.local.singleton

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys


object OunceLocalRepository {
    private const val PREF_KEY = "haskhey"
    private const val NATIVE_APP_KEY = "appkey"
    private const val REFRESH_TOKEN_KEY = "refresh"
    private const val USER_TOKEN_KEY = "access"
    private const val CAT_INDEX_KEY = "cat"
    private const val FROM_KEY = "from"
    private const val CAT_NAME_KEY = "catName"
    private const val CAT_REVIEW_COUNT = "reviewCount"
    private const val CAT_INDEX_SELECTED = "selectedCatIndex"
    const val KAKAO = "kakao"
    const val GOOGLE = "google"

    private val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
    private lateinit var encryptedRepository: SharedPreferences

    fun init(context: Context) {
        encryptedRepository = EncryptedSharedPreferences.create(
            PREF_KEY,
            masterKeyAlias,
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
        encryptedRepository
            .edit()
            .putString(NATIVE_APP_KEY, "7171285de2b4a7896ab049321ffc8ed6")
            .apply()
    }

    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = edit()
        operation(editor)
        editor.apply()
    }

    var userRefreshToken: String
        get() = encryptedRepository.getString(REFRESH_TOKEN_KEY, "") ?: ""
        set(value) = encryptedRepository.edit { it.putString(REFRESH_TOKEN_KEY, value) }

    var userAccessToken: String
        get() = encryptedRepository.getString(USER_TOKEN_KEY, "") ?: ""
        set(value) = encryptedRepository.edit { it.putString(USER_TOKEN_KEY, value) }

    var catIndex: Int
        get() = encryptedRepository.getInt(CAT_INDEX_KEY, -1)
        set(value) = encryptedRepository.edit { it.putInt(CAT_INDEX_KEY, value) }

    var loginFrom: String
        get() = encryptedRepository.getString(FROM_KEY, "unregistered") ?: "unregistered"
        set(value) = encryptedRepository.edit { it.putString(FROM_KEY, value) }

    var catName: String
        get() = encryptedRepository.getString(CAT_NAME_KEY, "") ?: ""
        set(value) = encryptedRepository.edit { it.putString(CAT_NAME_KEY, value) }

    var reviewCount: Int
        get() = encryptedRepository.getInt(CAT_REVIEW_COUNT, -1)
        set(value) = encryptedRepository.edit { it.putInt(CAT_REVIEW_COUNT, value) }

    fun getKey(): String {
        return encryptedRepository.getString(NATIVE_APP_KEY, "")!!
    }
}