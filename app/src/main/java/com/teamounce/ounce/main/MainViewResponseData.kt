package com.teamounce.ounce.main

import com.google.gson.annotations.SerializedName

data class MainViewResponseData(
    @SerializedName("responseMessage")
    val responseMessage : String,
    @SerializedName("data")
    val data : MainViewCatInfoData
)