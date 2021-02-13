package com.teamounce.ounce.data.remote.repository

import com.teamounce.ounce.feed.model.RequestAddTag
import com.teamounce.ounce.feed.model.ResponseModifyTagInfo
import com.teamounce.ounce.review.model.ResponseTags

interface TagRepository {
    suspend fun getTags(catIndex: Int): ResponseTags
    suspend fun deleteTag(tagIndex: Int): ResponseModifyTagInfo
    suspend fun addTag(catIndex: Int, body: RequestAddTag): ResponseModifyTagInfo
}