package kr.co.are.searchcocktail.domain.entity.streamtext

import com.squareup.moshi.JsonClass

sealed class ParagraphChildEntity {
    @JsonClass(generateAdapter = true)
    data class SpeakerBlockEntity(
        val speaker: String,
        val time: Double,
        val type: String // "speaker-block"
    ) : ParagraphChildEntity()

    @JsonClass(generateAdapter = true)
    data class KaraokeEntity(
        val text: String,
        val type: String, // "karaoke"
        val s: Double,
        val e: Double
    ) : ParagraphChildEntity()
}