package kr.co.are.searchcocktail.data.remoteapicocktail

import kr.co.are.searchcocktail.data.remoteapicocktail.model.response.GetFilterByAlcoholicResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiCocktailService {

    @GET("v1/1/filter.php")
    suspend fun getFilterByAlcoholic(
        @Query("a") alcoholic: String = "Alcoholic",
    ): Response<GetFilterByAlcoholicResponse>

}
