package com.teamounce.ounce.data.remote.repository

import com.teamounce.ounce.register.model.RequestAddCatInfo
import com.teamounce.ounce.register.model.ResponseAddCatInfo

interface RegisterRepository {
    suspend fun registerCatInfo(catInfo: RequestAddCatInfo): ResponseAddCatInfo
}