package kr.co.are.searchcocktail.domain.repository

import kotlinx.coroutines.flow.Flow

interface ApiCocktailRepository {

    suspend fun getFilterByAlcoholic(): Flow<String>

}