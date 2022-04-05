package com.teamounce.ounce.feed.model

import com.google.gson.annotations.SerializedName

data class ResponseFeedReviewData(
    @SerializedName("data")
    val data: List<Data>,
    @SerializedName("responseMessage")
    val responseMessage: String
) {
    data class Data(
        @SerializedName("createdDate")
        val createdDate: String,
        @SerializedName("manufacturer")
        val manufacturer: String,
        @SerializedName("memo")
        val memo: String,
        @SerializedName("myImg")
        val myImg: String,
        @SerializedName("preference")
        val preference: Int,
        @SerializedName("productImg")
        val productImg: String,
        @SerializedName("productName")
        val productName: String,
        @SerializedName("reviewIndex")
        val reviewIndex: Int,
        @SerializedName("tag1")
        val tag1: String,
        @SerializedName("tag2")
        val tag2: String,
        @SerializedName("tag3")
        val tag3: String,
        @SerializedName("updatedDate")
        val updatedDate: String
    )
}