package com.teamounce.ounce.feed.model


import com.google.gson.annotations.SerializedName

data class ResponseModifyTagInfo(
    @SerializedName("data")
    val `data`: Int,
    @SerializedName("responseMessage")
    val responseMessage: String
)