package com.teamounce.ounce.review.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class ResponseSearch(
    @SerializedName("data")
    val resultList: List<Data>,
    @SerializedName("responseMessage")
    val responseMessage: String
) {
    @Parcelize
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
    ) : Parcelable
}