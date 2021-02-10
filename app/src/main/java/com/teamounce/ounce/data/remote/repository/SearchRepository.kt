package com.teamounce.ounce.data.remote.repository

import com.teamounce.ounce.review.model.ResponseSearch

interface SearchRepository {
    suspend fun search(query: String, catIndex: Int): ResponseSearch
}