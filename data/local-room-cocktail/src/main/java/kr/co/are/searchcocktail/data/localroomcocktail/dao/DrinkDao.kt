package kr.co.are.searchcocktail.data.localroomcocktail.dao

import androidx.room.*
import kr.co.are.searchcocktail.data.localroomcocktail.entity.TableFavoriteDrinkInfoEntity


@Dao
interface DrinkDao {
    //즐겨찾기 조회
    @Query("SELECT * FROM favorite_drink_info")
    fun selectAllFavoriteDrinkInfo(): List<TableFavoriteDrinkInfoEntity>

    //즐겨찾기 추가
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavoriteDrinkInfo(entity: TableFavoriteDrinkInfoEntity)

    //즐겨찾기 삭제
    @Query("DELETE FROM favorite_drink_info WHERE id = :id")
    fun deleteFavoriteDrinkInfoById(id: String)

    //전체 삭제
    @Query("DELETE FROM favorite_drink_info")
    fun deleteAllFavoriteDrinkInfo()
}
