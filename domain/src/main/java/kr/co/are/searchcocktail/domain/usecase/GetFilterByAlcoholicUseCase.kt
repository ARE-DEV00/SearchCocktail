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

class GetFilterByAlcoholicUseCase @Inject constructor(
    private val apiCocktailRepository: ApiCocktailRepository
) {
    suspend operator fun invoke(): Flow<ResultDomain<List<DrinkInfoEntity>>> {
        return channelFlow {
            apiCocktailRepository.getFilterByAlcoholic()
                .catch { exception ->
                    send(ResultDomain.Error(exception))
                }
                .collectLatest { result ->
                    when (result) {
                        is ResultData.Success -> {
                            send(ResultDomain.Success(result.data))
                        }
                        is ResultData.Error -> {
                            send(ResultDomain.Error(result.exception))
                        }
                        is ResultData.Loading -> {
                            send(ResultDomain.Loading)
                        }
                    }

                }
        }.flowOn(Dispatchers.IO)
    }
}