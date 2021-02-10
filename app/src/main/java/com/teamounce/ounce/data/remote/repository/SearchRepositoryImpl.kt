package com.teamounce.ounce.data.remote.repository

import com.teamounce.ounce.data.remote.api.SearchService
import com.teamounce.ounce.review.model.ResponseSearch
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(private val service: SearchService) :
    SearchRepository {
    override suspend fun search(query: String, catIndex: Int): ResponseSearch =
        service.search(query, catIndex)
}