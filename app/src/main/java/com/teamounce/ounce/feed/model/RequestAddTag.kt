package com.teamounce.ounce.feed.model

import com.google.gson.annotations.SerializedName

data class RequestAddTag(
    @SerializedName("tag")
    val tag: String
)
