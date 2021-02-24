package com.teamounce.ounce.feed.model


import com.google.gson.annotations.SerializedName

data class RequestReview(
    @SerializedName("reviewIndex")
    val reviewIndex: Int
)