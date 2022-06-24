package com.teamounce.ounce.login.model

import com.google.gson.annotations.SerializedName

data class RequestGoogleLogin(
    @SerializedName("token")
    val token: String
)
