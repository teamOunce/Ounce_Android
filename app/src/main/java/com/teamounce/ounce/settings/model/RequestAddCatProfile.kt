package com.teamounce.ounce.settings.model


import com.google.gson.annotations.SerializedName

data class RequestAddCatProfile(
    @SerializedName("catName")
    val catName: String,
    @SerializedName("meetDate")
    val meetDate: String
)