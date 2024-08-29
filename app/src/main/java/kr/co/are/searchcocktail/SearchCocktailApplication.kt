package kr.co.are.searchcocktail

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import kr.co.are.searchcocktail.core.buildconfig.BuildConfig
import timber.log.Timber


@HiltAndroidApp
class SearchCocktailApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        if(BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree())
        }
    }

}