package kr.co.are.searchcocktail.data.remoteapicocktail.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kr.co.are.searchcocktail.data.remoteapicocktail.ApiCocktailService
import kr.co.are.searchcocktail.domain.entity.DrinkInfoEntity
import kr.co.are.searchcocktail.domain.model.ResultData
import kr.co.are.searchcocktail.domain.repository.ApiCocktailRepository
import java.net.UnknownHostException
import javax.inject.Inject

class ApiCocktailRepositoryImpl @Inject constructor(
    private val apiCocktailService: ApiCocktailService,
) : ApiCocktailRepository {
    override suspend fun getFilterByAlcoholic(): Flow<ResultData<List<DrinkInfoEntity>>> {
        return flow {
            try {
                emit(ResultData.Loading)
                val response = apiCocktailService.getFilterByAlcoholic()
                if (response.isSuccessful) {
                    val list = response.body()?.drinks
                    if (list?.isNotEmpty() == true) {
                        emit(ResultData.Success(list.map {
                            DrinkInfoEntity(
                                id = it.idDrink,
                                name = it.strDrink,
                                thumbUrl = it.strDrinkThumb,
                            )
                        }))
                    } else {
                        emit(ResultData.Success(emptyList()))
                    }

                } else {
                    emit(ResultData.Error(Exception("API Error: ${response.message()}")))
                }
            } catch (uhe: UnknownHostException) {
                emit(ResultData.Error(uhe))
            } catch (t: Throwable) {
                emit(ResultData.Error(t))
            }
        }
    }
}
