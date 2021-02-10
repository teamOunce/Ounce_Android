package com.teamounce.ounce.data.remote.repository

import com.teamounce.ounce.data.remote.api.LoginService
import com.teamounce.ounce.login.model.RequestGoogleLogin
import com.teamounce.ounce.login.model.RequestKakaoLogin
import com.teamounce.ounce.login.model.ResponseLogin
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(private val loginService: LoginService) :
    LoginRepository {
    override suspend fun googleLogin(token: String): ResponseLogin =
        loginService.postUserByGoogleLogin(
            RequestGoogleLogin(token)
        )

    override suspend fun kakaoLogin(id: String): ResponseLogin =
        loginService.postUserByKakaoLogin(
            RequestKakaoLogin(id)
        )

    override suspend fun takeRefreshToken(refresh: String): ResponseLogin = loginService.refreshToken(refresh)
}