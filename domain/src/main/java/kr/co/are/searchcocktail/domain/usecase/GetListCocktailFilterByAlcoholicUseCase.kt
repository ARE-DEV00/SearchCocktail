package kr.co.are.searchcocktail.domain.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kr.co.are.searchcocktail.domain.entity.drink.DrinkInfoEntity
import kr.co.are.searchcocktail.domain.model.ResultData
import kr.co.are.searchcocktail.domain.model.ResultDomain
import kr.co.are.searchcocktail.domain.repository.ApiCocktailRepository
import kr.co.are.searchcocktail.domain.repository.DatabaseCocktailRepository
import javax.inject.Inject

class GetListCocktailFilterByAlcoholicUseCase @Inject constructor(
    private val apiCocktailRepository: ApiCocktailRepository,
    private val getFavoriteCocktailByIdUseCase: GetFavoriteCocktailByIdUseCase,
    private val databaseCocktailRepository: DatabaseCocktailRepository
) {

    var favoriteCocktails: List<DrinkInfoEntity>? = null

    suspend operator fun invoke(): Flow<ResultDomain<List<DrinkInfoEntity>>> {
        return channelFlow {
            databaseCocktailRepository.getFavoriteDrinkInfoList()
                .catch { exception ->
                    send(ResultDomain.Error(exception, false))
                }
                .collectLatest { resultData ->
                    when (resultData) {
                        is ResultData.Success -> {
                            favoriteCocktails = resultData.data
                        }

                        is ResultData.Error -> {
                            send(
                                ResultDomain.Error(
                                    resultData.exception,
                                    resultData.isNetwork
                                )
                            )
                        }

                        ResultData.Loading -> {
                            send(ResultDomain.Loading)
                        }
                    }
                }
            apiCocktailRepository.getListCocktailFilterByAlcoholic()
                .catch { exception ->
                    send(ResultDomain.Error(exception, false))
                }
                .collectLatest { resultData ->
                    when (resultData) {
                        is ResultData.Success -> {
                            val favoriteCocktailsFlow = resultData.data.map { drink ->
                                getFavoriteCocktailByIdUseCase(drink.id)
                                    .map { favoriteResult ->
                                        when (favoriteResult) {
                                            is ResultDomain.Success -> {
                                                drink.isFavorite = favoriteResult.data != null
                                            }
                                            else -> {
                                                drink.isFavorite = false
                                            }
                                        }
                                        drink
                                    }
                            }

                            kotlinx.coroutines.flow.combine(favoriteCocktailsFlow) { drinks ->
                                drinks.toList()
                            }.collect { drinksWithFavoriteStatus ->
                                send(ResultDomain.Success(drinksWithFavoriteStatus))
                            }
                        }

                        is ResultData.Error -> {
                            send(
                                ResultDomain.Error(
                                    resultData.exception,
                                    resultData.isNetwork
                                )
                            )
                        }

                        is ResultData.Loading -> {
                            send(ResultDomain.Loading)
                        }
                    }

                }
        }.flowOn(Dispatchers.IO)
    }
}