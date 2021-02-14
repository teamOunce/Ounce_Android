package com.teamounce.ounce.data.remote.repository

import com.teamounce.ounce.data.remote.api.SettingService
import com.teamounce.ounce.settings.model.RequestAddCatProfile
import com.teamounce.ounce.settings.model.ResponseAddCatProfile
import com.teamounce.ounce.settings.model.ResponseCatProfiles
import javax.inject.Inject

class SettingRepositoryImpl @Inject constructor(private val service: SettingService) :
    SettingRepository {
    override suspend fun addCat(profile: RequestAddCatProfile): ResponseAddCatProfile =
        service.addCatProfile(profile)

    override suspend fun getCatProfiles(catIndex: Int): ResponseCatProfiles =
        service.getCatList(catIndex)
}