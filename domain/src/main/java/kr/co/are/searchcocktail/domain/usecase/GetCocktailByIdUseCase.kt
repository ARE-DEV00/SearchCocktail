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

class GetCocktailByIdUseCase @Inject constructor(
    private val apiCocktailRepository: ApiCocktailRepository,
    private val getFavoriteCocktailByIdUseCase: GetFavoriteCocktailByIdUseCase,
) {
    suspend operator fun invoke(
        id:String
    ): Flow<ResultDomain<DrinkInfoEntity?>> {
        return channelFlow {

            apiCocktailRepository.getCocktailById(id = id)
                .catch { exception ->
                    send(ResultDomain.Error(exception, false))
                }
                .collectLatest { resultData ->
                    when (resultData) {
                        is ResultData.Success -> {
                            val drink = resultData.data
                            if (drink != null) {
                                // 즐겨찾기 여부를 확인하는 로직 추가
                                getFavoriteCocktailByIdUseCase(drink.id)
                                    .catch { exception ->
                                        // 예외 발생 시 즐겨찾기 상태는 false로 간주
                                        drink.isFavorite = false
                                    }
                                    .collect { favoriteResult ->
                                        when (favoriteResult) {
                                            is ResultDomain.Success -> {
                                                drink.isFavorite = favoriteResult.data != null
                                            }
                                            else -> {
                                                drink.isFavorite = false
                                            }
                                        }
                                    }
                            }
                            // 최종 결과를 전달
                            send(ResultDomain.Success(drink))
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


        }.flowOn(Dispatchers.IO)
    }
}