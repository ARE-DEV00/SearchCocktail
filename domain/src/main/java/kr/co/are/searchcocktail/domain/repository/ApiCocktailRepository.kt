package kr.co.are.searchcocktail.domain.repository

import kotlinx.coroutines.flow.Flow
import kr.co.are.searchcocktail.domain.entity.DrinkInfoEntity
import kr.co.are.searchcocktail.domain.model.ResultData

interface ApiCocktailRepository {

    suspend fun getFilterByAlcoholic(): Flow<ResultData<List<DrinkInfoEntity>>>

}