package com.teamounce.ounce.settings.model


import com.google.gson.annotations.SerializedName

data class ResponseExpire(
    @SerializedName("data")
    val `data`: Int?,
    @SerializedName("responseMessage")
    val responseMessage: String
)