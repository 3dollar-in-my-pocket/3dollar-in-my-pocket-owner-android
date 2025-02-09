package app.threedollars.di

import android.os.Build
import app.threedollars.data.BuildConfig
import app.threedollars.db.DataStoreManager
import app.threedollars.network.NetworkService
import app.threedollars.source.LocalDataSourceImpl.Companion.ACCESS_TOKEN
import app.threedollars.source.LocalDataSourceImpl.Companion.APPLICATION_ID
import app.threedollars.source.LocalDataSourceImpl.Companion.VERSION_NAME
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter.Factory
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideLoggerInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = if (BuildConfig.BUILD_TYPE == "debug") HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
    }

    @Provides
    @Singleton
    fun provideHeaderInterceptor(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        dataStoreManager: DataStoreManager,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor {
                val token = runBlocking { dataStoreManager.getStringData(ACCESS_TOKEN).firstOrNull() ?: "" }
                val versionName = runBlocking { dataStoreManager.getStringData(VERSION_NAME).firstOrNull() ?: "" }
                val applicationId = runBlocking { dataStoreManager.getStringData(APPLICATION_ID).firstOrNull() ?: "" }

                val request = it.request().newBuilder()
                    .removeHeader("User-Agent")
                    .addHeader("User-Agent", versionName + " (${applicationId}); " + Build.VERSION.SDK_INT)
                    .addHeader("Authorization", "Bearer $token")
                    .addHeader("X-ANDROID-SERVICE-VERSION", BuildConfig.BUILD_TYPE + "_" + versionName)
                    .build()
                it.proceed(request)
            }
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofitBuilder(
        okHttpClient: OkHttpClient,
        jsonConverter: Factory,
    ): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(jsonConverter)
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        encodeDefaults = true
    }

    @Provides
    @Singleton
    fun provideConverterFactory(
        json: Json,
    ): Factory {
        return json.asConverterFactory("application/json".toMediaType())
    }

    @Provides
    @Singleton
    fun provideNetworkService(retrofit: Retrofit): NetworkService = retrofit.create(NetworkService::class.java)
}