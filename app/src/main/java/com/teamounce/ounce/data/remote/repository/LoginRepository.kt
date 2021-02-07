package com.teamounce.ounce.data.remote.repository

import com.teamounce.ounce.login.model.ResponseLogin

interface LoginRepository {
    suspend fun googleLogin(token: String): ResponseLogin
    suspend fun kakaoLogin(id: String): ResponseLogin
    suspend fun takeRefreshToken(): ResponseLogin
}