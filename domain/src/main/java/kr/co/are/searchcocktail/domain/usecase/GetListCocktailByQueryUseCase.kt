package kr.co.are.searchcocktail.domain.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kr.co.are.searchcocktail.domain.entity.drink.DrinkInfoEntity
import kr.co.are.searchcocktail.domain.model.ResultData
import kr.co.are.searchcocktail.domain.model.ResultDomain
import kr.co.are.searchcocktail.domain.repository.ApiCocktailRepository
import javax.inject.Inject

class GetListCocktailByQueryUseCase @Inject constructor(
    private val apiCocktailRepository: ApiCocktailRepository,
    private val getFavoriteCocktailByIdUseCase: GetFavoriteCocktailByIdUseCase,
) {
    private var resultSearchCocktails: List<DrinkInfoEntity>? = null

    suspend operator fun invoke(
        query: String,
        isRefresh: Boolean
    ): Flow<ResultDomain<List<DrinkInfoEntity>>> {
        return channelFlow {
            if (isRefresh) {
                resultSearchCocktails = null
            }

            println("#### searchCocktails: ${resultSearchCocktails?.size}")
            if (query.isNotEmpty()) {
                if (resultSearchCocktails != null) {
                    send(ResultDomain.Success(searchFilter(resultSearchCocktails, query)))
                } else {
                    apiCocktailRepository.getListAllCocktailByFirstLetter(
                        firstLetter = query.first().toString()
                    )
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

                                    combine(favoriteCocktailsFlow) { drinks ->
                                        drinks.toList()
                                    }.collectLatest { drinksWithFavoriteList ->
                                        resultSearchCocktails = drinksWithFavoriteList
                                        send(ResultDomain.Success(searchFilter(resultSearchCocktails, query)))
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
                }
            } else {
                resultSearchCocktails = null
            }

        }.flowOn(Dispatchers.IO)
    }

    private fun searchFilter(searchCocktails: List<DrinkInfoEntity>?, query: String): List<DrinkInfoEntity> {
        return searchCocktails?.filter {
            it.name!!.uppercase().contains(query.uppercase())
        } ?: emptyList()

    }
}
