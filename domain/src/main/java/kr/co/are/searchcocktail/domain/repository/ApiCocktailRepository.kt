package kr.co.are.searchcocktail.domain.repository

import kotlinx.coroutines.flow.Flow
import kr.co.are.searchcocktail.domain.entity.drink.DrinkInfoEntity
import kr.co.are.searchcocktail.domain.model.ResultData

interface ApiCocktailRepository {

    suspend fun getListCocktailFilterByAlcoholic(): Flow<ResultData<List<DrinkInfoEntity>>>

    suspend fun getListAllCocktailByFirstLetter(firstLetter:String): Flow<ResultData<List<DrinkInfoEntity>>>

    suspend fun getCocktailById(id: String): Flow<ResultData<DrinkInfoEntity?>>
}