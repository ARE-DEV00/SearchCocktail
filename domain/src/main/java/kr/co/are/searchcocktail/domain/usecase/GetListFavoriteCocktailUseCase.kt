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

class GetListFavoriteCocktailUseCase @Inject constructor(
    private val databaseCocktailRepository: DatabaseCocktailRepository
) {
    suspend operator fun invoke(): Flow<ResultDomain<List<DrinkInfoEntity>>> {
        return channelFlow {
            databaseCocktailRepository.getFavoriteDrinkInfoList()
                .catch { exception ->
                    send(ResultDomain.Error(exception, false))
                }
                .collectLatest { resultData ->
                    when (resultData) {
                        is ResultData.Success -> {
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

                        ResultData.Loading -> {
                            send(ResultDomain.Loading)
                        }
                    }
                }

        }.flowOn(Dispatchers.IO)
    }
}