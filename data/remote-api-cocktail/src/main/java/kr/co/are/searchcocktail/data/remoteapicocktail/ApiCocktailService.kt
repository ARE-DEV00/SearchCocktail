package kr.co.are.searchcocktail.data.remoteapicocktail

import kr.co.are.searchcocktail.data.remoteapicocktail.model.response.GetCocktailByIdResponse
import kr.co.are.searchcocktail.data.remoteapicocktail.model.response.GetFilterByAlcoholicResponse
import kr.co.are.searchcocktail.data.remoteapicocktail.model.response.GetListAllCocktailByFirstLetterResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiCocktailService {

    @GET("v1/{api-key}/filter.php")
    suspend fun getFilterByAlcoholic(
        @Path("api-key") apiKey: String = "1",
        @Query("a") alcoholic: String = "Alcoholic",
    ): Response<GetFilterByAlcoholicResponse>

    @GET("v1/{api-key}/search.php")
    suspend fun getListAllCocktailByFirstLetter(
        @Path("api-key") apiKey: String = "1",
        @Query("f") firstLetter: String,
    ): Response<GetListAllCocktailByFirstLetterResponse>

    @GET("v1/{api-key}/lookup.php")
    suspend fun getCocktailById(
        @Path("api-key") apiKey: String = "1",
        @Query("i") id: String,
    ): Response<GetCocktailByIdResponse>


}
