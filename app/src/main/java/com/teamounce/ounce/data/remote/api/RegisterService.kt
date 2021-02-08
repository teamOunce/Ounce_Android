package com.teamounce.ounce.data.remote.api

import com.teamounce.ounce.register.model.RequestAddCatInfo
import com.teamounce.ounce.register.model.ResponseAddCatInfo
import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterService {
    @POST
    suspend fun registerCatInfo(
        @Body catInfo: RequestAddCatInfo
    ): ResponseAddCatInfo
}