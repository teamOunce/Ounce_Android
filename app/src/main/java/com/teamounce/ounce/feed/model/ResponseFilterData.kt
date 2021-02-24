package com.teamounce.ounce.feed.model

data class ResponseFilterData(
    val data: Data,
    val responseMessage: String
) {
    data class Data(
        val manu: List<String>,
        val tag: List<String>,
        val type: List<String>
    )
}