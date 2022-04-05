package com.teamounce.ounce.main

import com.google.gson.annotations.SerializedName

data class MainViewCatInfoData(
    @SerializedName("catName")
    val catName : String,
    @SerializedName("fromMeet")
    val fromMeet : Int,
    @SerializedName("reviewCount")
    val reviewCount : Int
)