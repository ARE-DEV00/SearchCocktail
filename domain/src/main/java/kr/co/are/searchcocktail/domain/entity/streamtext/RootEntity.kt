package kr.co.are.searchcocktail.domain.entity.streamtext

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RootEntity(
    val type: String, // "root"
    val children: List<ParagraphEntity>
)