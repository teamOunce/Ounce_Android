package com.teamounce.ounce.feed.model


import com.google.gson.annotations.SerializedName

data class Feed(
    @SerializedName("data")
    val catFoodFeeds: List<CatFoodFeed>,
    @SerializedName("responseMessage")
    val responseMessage: String
) {
    data class CatFoodFeed(
        @SerializedName("manufacturer")
        val manufacturer: String,
        @SerializedName("memo")
        val memo: String,
        @SerializedName("preference")
        val preference: Int,
        @SerializedName("productImg")
        val productImg: String,
        @SerializedName("productIndex")
        val productIndex: Int,
        @SerializedName("productName")
        val productName: String,
        @SerializedName("reviewMoment")
        val reviewMoment: String,
        @SerializedName("tag1")
        val tag1: String,
        @SerializedName("tag2")
        val tag2: String,
        @SerializedName("tag3")
        val tag3: String
    )
}