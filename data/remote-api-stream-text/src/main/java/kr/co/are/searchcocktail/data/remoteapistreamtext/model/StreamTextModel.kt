package kr.co.are.searchcocktail.data.remoteapistreamtext.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class StreamTextModel(
    @Json(name = "id")
    val id: String,
    @Json(name = "title")
    val title: String,
    @Json(name = "videoUrl")
    val videoUrl: String,
    @Json(name = "streamText")
    val streamText: String,
    @Json(name = "createdAt")
    val createdAt: String,
    @Json(name = "updatedAt")
    val updatedAt: String
)