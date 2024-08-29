package kr.co.are.searchcocktail.data.localroomcocktail.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import kr.co.are.searchcocktail.data.localroomcocktail.converter.DateTypeConverter
import kr.co.are.searchcocktail.data.localroomcocktail.dao.DrinkDao
import kr.co.are.searchcocktail.data.localroomcocktail.entity.TableFavoriteDrinkInfoEntity

@Database(
    entities = [TableFavoriteDrinkInfoEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(DateTypeConverter::class)
abstract class CocktailDatabase: RoomDatabase() {
    abstract fun cocktailDao(): DrinkDao
}