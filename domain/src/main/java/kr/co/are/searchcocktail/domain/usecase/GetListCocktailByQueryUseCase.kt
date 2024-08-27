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
import javax.inject.Inject

class GetListCocktailByQueryUseCase @Inject constructor(
    private val apiCocktailRepository: ApiCocktailRepository
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
                    send(ResultDomain.Success(searchCocktailsFilter(query)))
                } else {
                    apiCocktailRepository.getListAllCocktailByFirstLetter(
                        firstLetter = query.first().toString()
                    )
                        .catch { exception ->
                            send(ResultDomain.Error(exception, false))
                        }
                        .collect { resultData ->
                            when (resultData) {
                                is ResultData.Success -> {
                                    searchCocktails = resultData.data
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
            }

        }.flowOn(Dispatchers.IO)
    }

    private fun searchCocktailsFilter(query: String): List<DrinkInfoEntity> {
        val searchResult = searchCocktails?.filter {
            it.name!!.uppercase().contains(query.uppercase())
        }
        return searchResult ?: emptyList()
    }

}