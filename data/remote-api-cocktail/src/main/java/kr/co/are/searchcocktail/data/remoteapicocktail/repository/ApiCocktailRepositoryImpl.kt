package kr.co.are.searchcocktail.data.remoteapicocktail.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kr.co.are.searchcocktail.data.remoteapicocktail.ApiCocktailService
import kr.co.are.searchcocktail.domain.entity.drink.DrinkInfoEntity
import kr.co.are.searchcocktail.domain.model.ResultData
import kr.co.are.searchcocktail.domain.repository.ApiCocktailRepository
import java.io.EOFException
import java.net.UnknownHostException
import javax.inject.Inject

class ApiCocktailRepositoryImpl @Inject constructor(
    private val apiCocktailService: ApiCocktailService,
) : ApiCocktailRepository {
    override suspend fun getListCocktailFilterByAlcoholic(): Flow<ResultData<List<DrinkInfoEntity>>> {
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
                    emit(ResultData.Error(Exception("API Error: ${response.message()}"), false))
                }
            } catch (uhe: UnknownHostException) {
                emit(ResultData.Error(uhe, true))
            } catch (t: Throwable) {
                emit(ResultData.Error(t, false))
            }
        }
    }

    override suspend fun getListAllCocktailByFirstLetter(firstLetter:String): Flow<ResultData<List<DrinkInfoEntity>>> {
        return flow {
            try {
                emit(ResultData.Loading)
                val response = apiCocktailService.getListAllCocktailByFirstLetter(firstLetter = firstLetter)
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
                    emit(ResultData.Error(Exception("API Error: ${response.message()}"), false))
                }
            } catch (uhe: UnknownHostException) {
                emit(ResultData.Error(uhe, true))
            } catch (t: EOFException) {
                //Cocktail API 에서 인코딩된 파라미터가 들어가는 경우, EOFException 발생
                //이 경우, 빈 리스트를 반환
                emit(ResultData.Success(emptyList()))
            } catch (t: Throwable) {
                emit(ResultData.Error(t, false))
            }
        }
    }
}
