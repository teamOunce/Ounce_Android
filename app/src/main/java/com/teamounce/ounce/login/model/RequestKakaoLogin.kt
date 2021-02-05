package com.teamounce.ounce.login.model


import com.google.gson.annotations.SerializedName

data class RequestKakaoLogin(
    @SerializedName("id")
    val id: String
)