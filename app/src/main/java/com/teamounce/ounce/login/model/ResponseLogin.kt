package com.teamounce.ounce.login.model


import com.google.gson.annotations.SerializedName

data class ResponseLogin(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("responseMessage")
    val responseMessage: String
) {
    data class Data(
        @SerializedName("catCount")
        val catCount: Int,
        @SerializedName("recentCatIndex")
        val recentCatIndex: Int,
        @SerializedName("refresh")
        val refresh: String,
        @SerializedName("token")
        val token: String,
        @SerializedName("user")
        val user: Boolean
    )
}