package com.teamounce.ounce.feed.model


import com.google.gson.annotations.SerializedName

data class Review(
    @SerializedName("data")
    val catFoodReview: CatFoodReview,
    @SerializedName("responseMessage")
    val responseMessage: String
) {
    data class CatFoodReview(
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
        @SerializedName("createdDate")
        val createdDate: String,
        @SerializedName("updatedDate")
        val updatedDate: String,
        @SerializedName("tag1")
        val tag1: String,
        @SerializedName("tag2")
        val tag2: String,
        @SerializedName("tag3")
        val tag3: String
    )
}