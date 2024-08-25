package kr.co.are.searchcocktail.data.remoteapicocktail.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kr.co.are.searchcocktail.data.remoteapicocktail.ApiCocktailService
import kr.co.are.searchcocktail.domain.repository.ApiCocktailRepository
import retrofit2.Retrofit
import java.net.UnknownHostException
import javax.inject.Inject

class ApiCocktailRepositoryImpl @Inject constructor(
    private val apiCocktailService: ApiCocktailService,
    private val retrofit: Retrofit,
)  : ApiCocktailRepository {
    override suspend fun getFilterByAlcoholic(): Flow<String> {
        return flow {
            try {
                val response = apiCocktailService.getFilterByAlcoholic()


                if (response.isSuccessful) {
                    emit(response.body().toString())
                } else {
                }
            } catch (uhe: UnknownHostException) {
                throw uhe
            } catch (t: Throwable) {
                t.printStackTrace()
                throw t
            }
        }
    }
}