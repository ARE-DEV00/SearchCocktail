package kr.co.are.searchcocktail.data.localroomcocktail

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kr.co.are.searchcocktail.data.localroomcocktail.database.CocktailDatabase
import kr.co.are.searchcocktail.data.localroomcocktail.repository.CocktailDatabaseRepositoryImpl
import kr.co.are.searchcocktail.domain.repository.DatabaseCocktailRepository
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CocktailDatabaseModule {

    @Provides
    @Singleton
    @Named("CocktailDatabase")
    fun provideCocktailDatabase(@ApplicationContext context: Context): CocktailDatabase {
        return Room.databaseBuilder(
            context,
            CocktailDatabase::class.java,
            CocktailDataBaseKey.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideCocktailDatabaseRepository(@Named("CocktailDatabase") database: CocktailDatabase): DatabaseCocktailRepository =
        CocktailDatabaseRepositoryImpl(database)


}