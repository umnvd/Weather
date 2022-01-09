package com.umnvd.weather.di

import com.umnvd.weather.BuildConfig
import com.umnvd.weather.data.weather.WeatherApiService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

private const val WEATHER_BASE_URL = "https://api.openweathermap.org/"

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideWeatherApiRetrofit(): Retrofit {
        val language = Locale.getDefault().language
        val client = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val url = chain.request().url().newBuilder()
                    .addQueryParameter("appid", BuildConfig.OPEN_WEATHER_KEY)
                    .addQueryParameter("units", "metric")
                    .addQueryParameter("lang", language)
                    .build()
                val request = chain.request().newBuilder()
                    .url(url)
                    .build()
                chain.proceed(request)
            }
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .baseUrl(WEATHER_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun provideWeatherApiService(retrofit: Retrofit): WeatherApiService {
        return retrofit.create(WeatherApiService::class.java)
    }

}