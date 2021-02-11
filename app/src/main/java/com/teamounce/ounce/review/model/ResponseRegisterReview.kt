package com.teamounce.ounce.review.model


import com.google.gson.annotations.SerializedName

data class ResponseRegisterReview(
    @SerializedName("data")
    val `data`: Int,
    @SerializedName("responseMessage")
    val responseMessage: String
)