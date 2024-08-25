package kr.co.are.searchcocktail.data.remoteapicocktail

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kr.co.are.buildconfig.BuildConfig
import kr.co.are.searchcocktail.data.remoteapicocktail.repository.ApiCocktailRepositoryImpl
import kr.co.are.searchcocktail.domain.repository.ApiCocktailRepository
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiCocktailModule {

    @Provides
    fun provideCocktailBaseUrl() = BuildConfig.BASE_API_URL_COCKTAIL

    @Singleton
    @Provides
    @Named("CocktailOkHttpClient")
    fun provideOkHttpClient(): OkHttpClient {
        val httpClient: OkHttpClient.Builder = OkHttpClient.Builder()
        if(BuildConfig.BUILD_TYPE == "debug") {
            val loggingInterceptor = HttpLoggingInterceptor { message ->
                if (message.startsWith("{") || message.startsWith("[")) {
                    try {
                        val prettyJson = when {
                            message.startsWith("{") -> JSONObject(message).toString(4)
                            message.startsWith("[") -> JSONArray(message).toString(4)
                            else -> message
                        }
                        println(prettyJson)
                    } catch (e: JSONException) {
                        println(message)
                    }
                } else {
                    println(message)
                }
            }
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            httpClient.addInterceptor(loggingInterceptor)
        }

        return httpClient.readTimeout(20, TimeUnit.SECONDS)
            .addInterceptor { chain ->
                val request: Request = chain.request().newBuilder()
                    .build()
                chain.proceed(request)
            }
            .retryOnConnectionFailure(true)
            .build()
    }

    @Singleton
    @Provides
    @Named("CocktailRetrofit")
    fun provideRetrofit(@Named("CocktailOkHttpClient") okHttpClient: OkHttpClient): Retrofit {
        val moshi: Moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(provideCocktailBaseUrl())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

    }

    @Singleton
    @Provides
    @Named("CocktailApiService")
    fun provideApiService(@Named("CocktailRetrofit") retrofit: Retrofit): ApiCocktailService {
        return retrofit.create(ApiCocktailService::class.java)
    }


    @Singleton
    @Provides
    fun provideMainRepository(
        @Named("CocktailApiService") apiService: ApiCocktailService,
        @Named("CocktailRetrofit") retrofit: Retrofit
    ): ApiCocktailRepository =
        ApiCocktailRepositoryImpl(apiService, retrofit)
}

