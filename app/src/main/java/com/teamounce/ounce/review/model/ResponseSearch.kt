package com.teamounce.ounce.review.model


import com.google.gson.annotations.SerializedName

data class ResponseSearch(
    @SerializedName("data")
    val resultList: List<Data>,
    @SerializedName("responseMessage")
    val responseMessage: String
) {
    data class Data(
        @SerializedName("manufacturer")
        val manufacturer: String,
        @SerializedName("productImg")
        val productImg: String,
        @SerializedName("productIndex")
        val productIndex: Int,
        @SerializedName("productName")
        val productName: String,
        @SerializedName("type")
        val type: String
    )
}