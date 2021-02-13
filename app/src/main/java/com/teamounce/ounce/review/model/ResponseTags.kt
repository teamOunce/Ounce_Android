package com.teamounce.ounce.review.model


import com.google.gson.annotations.SerializedName

data class ResponseTags(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("responseMessage")
    val responseMessage: String
) {
    data class Data(
        @SerializedName("tag")
        val tag: String,
        @SerializedName("tagIndex")
        val tagIndex: Int
    )
}