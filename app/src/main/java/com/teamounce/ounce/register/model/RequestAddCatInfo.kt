package com.teamounce.ounce.register.model


import com.google.gson.annotations.SerializedName

data class RequestAddCatInfo(
    @SerializedName("birth")
    val birth: String,
    @SerializedName("catName")
    val catName: String,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("meetDate")
    val meetDate: String
)