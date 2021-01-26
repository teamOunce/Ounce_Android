package com.teamounce.ounce.feed.model


import com.google.gson.annotations.SerializedName

data class RequestFeed(
    @SerializedName("catIndex")
    val catIndex: Int
)