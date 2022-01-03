package com.umnvd.weather.di

import com.umnvd.weather.BuildConfig
import com.umnvd.weather.data.weather.WeatherApiService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.*
import javax.inject.Singleton

private const val BASE_URL = "https://api.openweathermap.org/"

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideWeatherApiService(): WeatherApiService {
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
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        return retrofit.create()
    }

}