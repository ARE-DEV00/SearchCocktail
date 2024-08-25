package kr.co.are.searchcocktail.domain.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kr.co.are.searchcocktail.domain.entity.DrinkInfoEntity
import kr.co.are.searchcocktail.domain.model.ResultData
import kr.co.are.searchcocktail.domain.model.ResultDomain
import kr.co.are.searchcocktail.domain.repository.ApiCocktailRepository
import javax.inject.Inject

class GetListCocktailFilterByAlcoholicUseCase @Inject constructor(
    private val apiCocktailRepository: ApiCocktailRepository
) {
    suspend operator fun invoke(): Flow<ResultDomain<List<DrinkInfoEntity>>> {
        return channelFlow {
            apiCocktailRepository.getListCocktailFilterByAlcoholic()
                .catch { exception ->
                    send(ResultDomain.Error(exception, false))
                }
                .collectLatest { resultData ->
                    when (resultData) {
                        is ResultData.Success -> {
                            send(ResultDomain.Success(resultData.data))
                        }

                        is ResultData.Error -> {
                            send(ResultDomain.Error(resultData.exception, resultData.isNetwork))
                        }

                        is ResultData.Loading -> {
                            send(ResultDomain.Loading)
                        }
                    }

                }
        }.flowOn(Dispatchers.IO)
    }
}