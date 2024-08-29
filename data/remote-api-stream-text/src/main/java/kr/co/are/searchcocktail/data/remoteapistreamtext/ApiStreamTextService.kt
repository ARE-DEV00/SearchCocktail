package kr.co.are.searchcocktail.data.remoteapistreamtext

import kr.co.are.searchcocktail.data.remoteapistreamtext.model.GetStreamTextByIdResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiStreamTextService {

    @GET("stream-text/{id}")
    suspend fun getFilterByAlcoholic(
        @Path("id") apiKey: String = "32151b3e-a6d6-4873-8eec-7055073490c3",
    ): Response<GetStreamTextByIdResponse>

}
