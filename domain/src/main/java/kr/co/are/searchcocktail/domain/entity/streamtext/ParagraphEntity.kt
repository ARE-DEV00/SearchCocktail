package kr.co.are.searchcocktail.domain.entity.streamtext

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ParagraphEntity(
    val type: String, // "paragraph"
    val children: List<ParagraphChildEntity>
)