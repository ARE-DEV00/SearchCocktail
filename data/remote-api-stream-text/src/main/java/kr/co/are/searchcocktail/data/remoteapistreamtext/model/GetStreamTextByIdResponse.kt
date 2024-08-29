package kr.co.are.searchcocktail.data.remoteapistreamtext.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GetStreamTextByIdResponse(
    @Json(name = "statusCode")
    val statusCode: Int,
    @Json(name = "message")
    val message: String,
    @Json(name = "data")
    val data: StreamTextModel
)