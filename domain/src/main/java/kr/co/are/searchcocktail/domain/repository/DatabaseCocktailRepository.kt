package kr.co.are.searchcocktail.domain.repository

import kotlinx.coroutines.flow.Flow
import kr.co.are.searchcocktail.domain.entity.drink.DrinkInfoEntity
import kr.co.are.searchcocktail.domain.model.ResultData

interface DatabaseCocktailRepository {
    suspend fun getFavoriteDrinkInfoList(): Flow<ResultData<List<DrinkInfoEntity>>>
    suspend fun getFavoriteDrinkInfo(id: String): Flow<ResultData<DrinkInfoEntity?>>
    suspend fun addFavoriteDrinkInfo(drinkInfoEntity: DrinkInfoEntity): Flow<ResultData<Boolean>>
    suspend fun deleteFavoriteDrinkInfo(id: String): Flow<ResultData<Boolean>>
    suspend fun deleteAllFavoriteDrinkInfo(): Flow<ResultData<Boolean>>

}