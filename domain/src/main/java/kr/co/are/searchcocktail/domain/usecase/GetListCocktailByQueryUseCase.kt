package kr.co.are.searchcocktail.domain.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kr.co.are.searchcocktail.domain.entity.drink.DrinkInfoEntity
import kr.co.are.searchcocktail.domain.model.ResultData
import kr.co.are.searchcocktail.domain.model.ResultDomain
import kr.co.are.searchcocktail.domain.repository.ApiCocktailRepository
import kr.co.are.searchcocktail.domain.repository.DatabaseCocktailRepository
import javax.inject.Inject

class GetListCocktailByQueryUseCase @Inject constructor(
    private val apiCocktailRepository: ApiCocktailRepository,
    private val databaseCocktailRepository: DatabaseCocktailRepository
) {
    private var searchCocktails: List<DrinkInfoEntity>? = null
    private var favoriteCocktails: List<DrinkInfoEntity>? = null

    suspend operator fun invoke(
        query: String,
        isRefresh: Boolean
    ): Flow<ResultDomain<List<DrinkInfoEntity>>> {
        return channelFlow {
            if (isRefresh) {
                searchCocktails = null
                favoriteCocktails = null
            }

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
                                    searchCocktails = resultData.data.map {
                                        val favoriteCocktail = favoriteCocktails?.find { favorite ->
                                            favorite.id == it.id
                                        }
                                        if (favoriteCocktail != null) {
                                            it.isFavorite = true
                                            it
                                        } else {
                                            it
                                        }
                                    }
                                    send(ResultDomain.Success(resultData.data))
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
                favoriteCocktails = null
            }

        }.flowOn(Dispatchers.IO)
    }
}