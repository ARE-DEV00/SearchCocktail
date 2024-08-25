package kr.co.are.searchcocktail.data.remoteapicocktail.model.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GetFilterByAlcoholicResponse(
    @Json(name = "drinks")
    val drinks: List<Drink>
) {
    @JsonClass(generateAdapter = true)
    data class Drink(
        @Json(name = "strDrink")
        val strDrink: String,
        @Json(name = "strDrinkThumb")
        val strDrinkThumb: String,
        @Json(name = "idDrink")
        val idDrink: String
    )
}