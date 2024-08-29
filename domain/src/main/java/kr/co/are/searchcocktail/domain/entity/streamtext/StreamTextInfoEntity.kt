package kr.co.are.searchcocktail.domain.entity.streamtext

data class StreamTextInfoEntity(
    val id: String,
    val title: String,
    val videoUrl: String,
    val streamText: String,
    var lexicalEntity: LexicalEntity? = null, // streamText가 Json으로 변환된 LexicalEntity
    val createdAt: String,
    val updatedAt: String,
)