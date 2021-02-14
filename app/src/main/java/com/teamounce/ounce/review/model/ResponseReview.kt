package com.teamounce.ounce.review.model


import com.google.gson.annotations.SerializedName

data class ResponseReview(
    @SerializedName("data")
    val `data`: Int?,
    @SerializedName("responseMessage")
    val responseMessage: String
)