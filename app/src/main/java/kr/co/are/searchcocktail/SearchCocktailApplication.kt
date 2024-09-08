package kr.co.are.searchcocktail

import android.app.Application
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.logEvent
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.HiltAndroidApp
import kr.co.are.searchcocktail.core.buildconfig.BuildConfig
import timber.log.Timber


@HiltAndroidApp
class SearchCocktailApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        Firebase.analytics.logEvent(FirebaseAnalytics.Event.APP_OPEN) {
            param(FirebaseAnalytics.Param.SOURCE, "app_open")
        }

        if(BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree())
        }
    }

}