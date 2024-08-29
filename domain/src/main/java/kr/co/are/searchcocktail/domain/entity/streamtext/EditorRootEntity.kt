package kr.co.are.searchcocktail.domain.entity.streamtext

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class EditorRootEntity(
    val root: RootEntity
)