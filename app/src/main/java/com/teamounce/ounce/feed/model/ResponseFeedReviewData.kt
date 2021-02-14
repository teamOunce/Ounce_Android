package com.teamounce.ounce.feed.model

data class ResponseFeedReviewData(
    val data: List<Data>,
    val responseMessage: String
) {
    data class Data(
        val createdDate: String,
        val manufacturer: String,
        val memo: String,
        val myImg: String,
        val preference: Int,
        val productImg: String,
        val productName: String,
        val reviewIndex: Int,
        val tag1: String,
        val tag2: String,
        val tag3: String,
        val updatedDate: String
    )
}