package com.teamounce.ounce.data.remote.repository

import com.teamounce.ounce.data.remote.api.TagService
import com.teamounce.ounce.feed.model.RequestAddTag
import com.teamounce.ounce.feed.model.ResponseModifyTagInfo
import com.teamounce.ounce.review.model.ResponseTags
import javax.inject.Inject

class TagRepositoryImpl @Inject constructor(
    private val tagService: TagService
) : TagRepository {
    override suspend fun getTags(catIndex: Int): ResponseTags = tagService.getTags(catIndex)

    override suspend fun deleteTag(tagIndex: Int): ResponseModifyTagInfo =
        tagService.deleteTag(tagIndex)

    override suspend fun addTag(catIndex: Int, body: RequestAddTag): ResponseModifyTagInfo =
        tagService.addTag(catIndex, body)
}