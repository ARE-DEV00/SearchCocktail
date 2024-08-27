package kr.co.are.searchcocktail.domain.model

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import kr.co.are.searchcocktail.domain.entity.streamtext.ParagraphChildEntity


class ParagraphChildAdapter {
    @FromJson
    fun fromJson(json: Map<String, Any>): ParagraphChildEntity {
        val type = json["type"] as? String
        return when (type) {
            "speaker-block" -> ParagraphChildEntity.SpeakerBlockEntity(
                speaker = json["speaker"] as String,
                time = (json["time"] as Number).toDouble(),
                type = type
            )
            "karaoke" -> ParagraphChildEntity.KaraokeEntity(
                text = json["text"] as String,
                type = type,
                s = (json["s"] as Number).toDouble(),
                e = (json["e"] as Number).toDouble()
            )
            else -> throw IllegalArgumentException("Unknown type: $type")
        }
    }

    @ToJson
    fun toJson(paragraphChild: ParagraphChildEntity): Map<String, Any> {
        return when (paragraphChild) {
            is ParagraphChildEntity.SpeakerBlockEntity -> mapOf(
                "speaker" to paragraphChild.speaker,
                "time" to paragraphChild.time,
                "type" to paragraphChild.type
            )
            is ParagraphChildEntity.KaraokeEntity -> mapOf(
                "text" to paragraphChild.text,
                "type" to paragraphChild.type,
                "s" to paragraphChild.s,
                "e" to paragraphChild.e
            )
        }
    }
}
