package com.teamounce.ounce.data.remote.repository

import com.teamounce.ounce.data.remote.api.RegisterService
import com.teamounce.ounce.register.model.RequestAddCatInfo
import com.teamounce.ounce.register.model.ResponseAddCatInfo
import javax.inject.Inject

class RegisterRepositoryImpl @Inject constructor(
    private val registerService: RegisterService
) : RegisterRepository {
    override suspend fun registerCatInfo(catInfo: RequestAddCatInfo): ResponseAddCatInfo =
        registerService.registerCatInfo(catInfo)
}