package kr.co.are.searchcocktail.domain.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kr.co.are.searchcocktail.domain.repository.ApiCocktailRepository
import javax.inject.Inject

class GetFilterByAlcoholicUseCase @Inject constructor(
    private val apiCocktailRepository: ApiCocktailRepository
) {
    suspend operator fun invoke(): Flow<String> {
        return channelFlow {
            apiCocktailRepository.getFilterByAlcoholic()
                .catch { exception ->
                    val errorMessage = "Error: ${exception.message}"
                    send(errorMessage)
                }
                .collectLatest { response ->
                    send(response)
                }
        }.flowOn(Dispatchers.IO)
    }
}