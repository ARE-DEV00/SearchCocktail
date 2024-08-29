package kr.co.are.searchcocktail.data.remoteapistreamtext

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kr.co.are.buildconfig.BuildConfig
import kr.co.are.searchcocktail.data.remoteapistreamtext.repository.ApiStreamTextRepositoryImpl
import kr.co.are.searchcocktail.domain.repository.ApiStreamTextRepository
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
object ApiStreamTextModule {

    @Provides
    fun provideStreamTextBaseUrl() = BuildConfig.BASE_API_URL_STREAM_TEXT

    @Singleton
    @Provides
    @Named("StreamTextOkHttpClient")
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
                    .addHeader("x-client-access", BuildConfig.KEY_STREAM_TEXT_API_KEY)
                    .addHeader("x-client-app-type", "search_cocktail")
                    .addHeader("x-client-app-version", "1.0.0")
                    .addHeader("x-client-app-build-number", "1")
                    .addHeader("x-client-app-platform", "android")
                    .addHeader("x-client-app-device-id", "android")
                    .build()
                chain.proceed(request)
            }
            .retryOnConnectionFailure(true)
            .build()
    }

    @Singleton
    @Provides
    @Named("StreamTextRetrofit")
    fun provideRetrofit(@Named("StreamTextOkHttpClient") okHttpClient: OkHttpClient): Retrofit {
        val moshi: Moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(provideStreamTextBaseUrl())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

    }

    @Singleton
    @Provides
    @Named("StreamTextApiService")
    fun provideApiService(@Named("StreamTextRetrofit") retrofit: Retrofit): ApiStreamTextService {
        return retrofit.create(ApiStreamTextService::class.java)
    }


    @Singleton
    @Provides
    fun provideMainRepository(
        @Named("StreamTextApiService") apiService: ApiStreamTextService,
    ): ApiStreamTextRepository =
        ApiStreamTextRepositoryImpl(apiService)
}

