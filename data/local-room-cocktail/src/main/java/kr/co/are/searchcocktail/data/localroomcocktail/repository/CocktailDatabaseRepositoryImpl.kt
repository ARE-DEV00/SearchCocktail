package kr.co.are.searchcocktail.data.localroomcocktail.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kr.co.are.searchcocktail.data.localroomcocktail.database.CocktailDatabase
import kr.co.are.searchcocktail.data.localroomcocktail.entity.TableFavoriteDrinkInfoEntity
import kr.co.are.searchcocktail.domain.entity.drink.DrinkInfoEntity
import kr.co.are.searchcocktail.domain.model.ResultData
import kr.co.are.searchcocktail.domain.repository.DatabaseCocktailRepository
import javax.inject.Inject

class CocktailDatabaseRepositoryImpl @Inject constructor(
    private val appDatabase: CocktailDatabase,
) : DatabaseCocktailRepository {
    override suspend fun getFavoriteDrinkInfoList(): Flow<ResultData<List<DrinkInfoEntity>>> {
        return flow {
            try {
                emit(ResultData.Loading)

                val favoriteCocktailList = appDatabase.cocktailDao().selectAllFavoriteDrinkInfo()

                emit(ResultData.Success(favoriteCocktailList.map {
                    DrinkInfoEntity(
                        id = it.id,
                        name = it.name,
                        category = it.category,
                        thumbUrl = it.thumbUrl,
                        isFavorite = true
                    )
                }))
            } catch (e: Exception) {
                emit(ResultData.Error(e, false))
            }
        }
    }

    override suspend fun getFavoriteDrinkInfo(id: String): Flow<ResultData<DrinkInfoEntity?>> {
        return flow {
            try {
                emit(ResultData.Loading)

                val favoriteCocktailInfo = appDatabase.cocktailDao().selectFavoriteDrinkInfo(id)

                if(favoriteCocktailInfo != null){
                    emit(ResultData.Success(
                        DrinkInfoEntity(
                            id = favoriteCocktailInfo.id,
                            name = favoriteCocktailInfo.name,
                            category = favoriteCocktailInfo.category,
                            thumbUrl = favoriteCocktailInfo.thumbUrl,
                            isFavorite = true
                        )
                    ))
                }else{
                    emit(ResultData.Error(Exception("Not Found"), false))
                }

            } catch (e: Exception) {
                emit(ResultData.Error(e, false))
            }
        }
    }

    override suspend fun addFavoriteDrinkInfo(drinkInfoEntity: DrinkInfoEntity): Flow<ResultData<Boolean>> {
        return flow {
            try {
                emit(ResultData.Loading)

                appDatabase.cocktailDao().insertFavoriteDrinkInfo(
                    TableFavoriteDrinkInfoEntity(
                        id = drinkInfoEntity.id,
                        name = drinkInfoEntity.name,
                        category = drinkInfoEntity.category,
                    )
                )
                emit(ResultData.Success(true))
            } catch (e: Exception) {
                emit(ResultData.Error(e, false))
            }
        }
    }

    override suspend fun deleteFavoriteDrinkInfo(id: String): Flow<ResultData<Boolean>> {
        return flow {
            try {
                emit(ResultData.Loading)

                appDatabase.cocktailDao().deleteFavoriteDrinkInfoById(id)
                emit(ResultData.Success(true))
            } catch (e: Exception) {
                emit(ResultData.Error(e, false))
            }
        }
    }

    override suspend fun deleteAllFavoriteDrinkInfo(): Flow<ResultData<Boolean>> {
        return flow {
            try {
                emit(ResultData.Loading)

                appDatabase.cocktailDao().deleteAllFavoriteDrinkInfo()
                emit(ResultData.Success(true))
            } catch (e: Exception) {
                emit(ResultData.Error(e, false))
            }
        }
    }
}
