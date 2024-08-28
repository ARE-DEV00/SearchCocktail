package kr.co.are.searchcocktail.domain.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kr.co.are.searchcocktail.domain.entity.drink.DrinkInfoEntity
import kr.co.are.searchcocktail.domain.model.ResultData
import kr.co.are.searchcocktail.domain.model.ResultDomain
import kr.co.are.searchcocktail.domain.repository.ApiCocktailRepository
import kr.co.are.searchcocktail.domain.repository.DatabaseCocktailRepository
import javax.inject.Inject

class GetListCocktailByQueryUseCase @Inject constructor(
    private val apiCocktailRepository: ApiCocktailRepository,
    private val getFavoriteCocktailByIdUseCase: GetFavoriteCocktailByIdUseCase,
) {
    private var searchCocktails: List<DrinkInfoEntity>? = null

    suspend operator fun invoke(
        query: String,
        isRefresh: Boolean
    ): Flow<ResultDomain<List<DrinkInfoEntity>>> {
        return channelFlow {
            if (isRefresh) {
                searchCocktails = null
            }

            println("#### searchCocktails: ${searchCocktails?.size}")
            if (query.isNotEmpty()) {
                if (searchCocktails != null) {
                    val searchResult = searchCocktails?.filter {
                        it.name!!.uppercase().contains(query.uppercase())
                    }
                    println("#### searchCocktails- 2: ${searchResult?.size}")
                    if (searchResult != null) {
                        send(ResultDomain.Success(searchResult))
                    } else {
                        send(ResultDomain.Success(emptyList()))
                    }
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
                                    }.collect { drinksWithFavoriteStatus ->
                                        searchCocktails = drinksWithFavoriteStatus
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
                }
            } else {
                searchCocktails = null
            }

        }.flowOn(Dispatchers.IO)
    }
}
