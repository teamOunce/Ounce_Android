package com.teamounce.ounce.feed.model

import com.google.gson.annotations.SerializedName

data class ResponseFilterData(
    @SerializedName("data")
    val data: Data,
    @SerializedName("responseMessage")
    val responseMessage: String
) {
    data class Data(
        @SerializedName("manu")
        val manu: List<String>,
        @SerializedName("tag")
        val tag: List<String>,
        @SerializedName("type")
        val type: List<String>
    )
}