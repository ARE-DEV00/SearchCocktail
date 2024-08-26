package kr.co.are.searchcocktail.domain.entity

data class StreamTextInfoEntity(
    val id: String,
    val title: String,
    val videoUrl: String,
    val streamText: String,
    val createdAt: String,
    val updatedAt: String
)