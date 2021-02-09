package com.teamounce.ounce.register.model


import com.google.gson.annotations.SerializedName

data class ResponseAddCatInfo(
    @SerializedName("data")
    val `data`: Int?,
    @SerializedName("responseMessage")
    val responseMessage: String?
)