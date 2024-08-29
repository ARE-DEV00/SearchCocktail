package kr.co.are.searchcocktail.data.remoteapicocktail.model.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GetCocktailByIdResponse(
    @Json(name = "drinks")
    val drinks: List<DrinkModel>?
)