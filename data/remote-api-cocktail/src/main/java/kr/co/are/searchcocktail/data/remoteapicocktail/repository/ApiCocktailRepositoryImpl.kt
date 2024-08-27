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

    override suspend fun getListAllCocktailByFirstLetter(firstLetter: String): Flow<ResultData<List<DrinkInfoEntity>>> {
        return flow {
            try {
                emit(ResultData.Loading)
                val response =
                    apiCocktailService.getListAllCocktailByFirstLetter(firstLetter = firstLetter)
                if (response.isSuccessful) {
                    val list = response.body()?.drinks
                    if (list?.isNotEmpty() == true) {
                        emit(ResultData.Success(list.map {
                            DrinkInfoEntity(
                                id = it.idDrink,
                                name = it.strDrink,
                                thumbUrl = it.strDrinkThumb,
                                category = it.strCategory,
                                alcoholic = it.strAlcoholic,
                                glass = it.strGlass,
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

    override suspend fun getCocktailById(id: String): Flow<ResultData<DrinkInfoEntity?>> {
        return flow {
            try {
                emit(ResultData.Loading)
                val response = apiCocktailService.getCocktailById(id = id)
                if (response.isSuccessful) {
                    val list = response.body()?.drinks
                    if (list?.isNotEmpty() == true) {
                        emit(ResultData.Success(list.map {
                            DrinkInfoEntity(
                                id = it.idDrink,
                                name = it.strDrink,
                                thumbUrl = it.strDrinkThumb,
                                category = it.strCategory,
                                alcoholic = it.strAlcoholic,
                                glass = it.strGlass,
                                instructions = it.strInstructions,
                                instructionsEs = it.strInstructionsEs,
                                instructionsDe = it.strInstructionsDe,
                                instructionsFr = it.strInstructionsFr,
                                instructionsIt = it.strInstructionsIt,
                                instructionsZhHans = it.strInstructionsZhHans,
                                instructionsZhHant = it.strInstructionsZhHant,
                                ingredient1 = it.strIngredient1,
                                ingredient2 = it.strIngredient2,
                                ingredient3 = it.strIngredient3,
                                ingredient4 = it.strIngredient4,
                                ingredient5 = it.strIngredient5,
                                ingredient6 = it.strIngredient6,
                                ingredient7 = it.strIngredient7,
                                ingredient8 = it.strIngredient8,
                                ingredient9 = it.strIngredient9,
                                ingredient10 = it.strIngredient10,
                                ingredient11 = it.strIngredient11,
                                ingredient12 = it.strIngredient12,
                                ingredient13 = it.strIngredient13,
                                ingredient14 = it.strIngredient14,
                                ingredient15 = it.strIngredient15,

                                measure1 = it.strMeasure1,
                                measure2 = it.strMeasure2,
                                measure3 = it.strMeasure3,
                                measure4 = it.strMeasure4,
                                measure5 = it.strMeasure5,
                                measure6 = it.strMeasure6,
                                measure7 = it.strMeasure7,
                                measure8 = it.strMeasure8,
                                measure9 = it.strMeasure9,
                                measure10 = it.strMeasure10,
                                measure11 = it.strMeasure11,
                                measure12 = it.strMeasure12,
                                measure13 = it.strMeasure13,
                                measure14 = it.strMeasure14,
                                measure15 = it.strMeasure15,
                                dateModified = it.dateModified
                            )
                        }.first()))
                    } else {
                        emit(ResultData.Success(null))
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
}
