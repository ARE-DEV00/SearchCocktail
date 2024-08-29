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

class GetListSyncFavoriteCocktailUseCase @Inject constructor(
    private val getFavoriteCocktailByIdUseCase: GetFavoriteCocktailByIdUseCase,
) {
    suspend operator fun invoke(drinks: List<DrinkInfoEntity>): Flow<ResultDomain<List<DrinkInfoEntity>>> {
        return channelFlow {
            val favoriteCocktailsFlow = drinks.map { drink ->
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
            }.collectLatest { drinksWithFavoriteList ->
                send(ResultDomain.Success(drinksWithFavoriteList))
            }
        }.flowOn(Dispatchers.IO)
    }
}