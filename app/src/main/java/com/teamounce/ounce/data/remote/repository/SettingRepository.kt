package com.teamounce.ounce.data.remote.repository

import com.teamounce.ounce.settings.model.RequestAddCatProfile
import com.teamounce.ounce.settings.model.ResponseAddCatProfile
import com.teamounce.ounce.settings.model.ResponseCatProfiles

interface SettingRepository {
    suspend fun addCat(profile: RequestAddCatProfile): ResponseAddCatProfile
    suspend fun getCatProfiles(catIndex: Int): ResponseCatProfiles
}