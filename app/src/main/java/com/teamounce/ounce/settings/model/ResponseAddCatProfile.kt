package com.teamounce.ounce.settings.model


import com.google.gson.annotations.SerializedName

data class ResponseAddCatProfile(
    @SerializedName("data")
    val `data`: Int?,
    @SerializedName("responseMessage")
    val responseMessage: String
)