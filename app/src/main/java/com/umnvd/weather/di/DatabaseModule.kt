package com.umnvd.weather.di

import android.content.Context
import androidx.room.Room
import com.umnvd.weather.data.AppDatabase
import com.umnvd.weather.data.cities.cities.CitiesDao
import com.umnvd.weather.data.cities.utils.CitiesPrePopulationCallback
import com.umnvd.weather.data.weather.weather_forecast.WeatherForecastDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    private lateinit var databaseInstance: AppDatabase

    @Provides
    @Singleton
    fun provideAppDatabase(context: Context): AppDatabase {
        databaseInstance = Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "weather.db"
        )
            .addCallback(CitiesPrePopulationCallback(context, databaseInstance))
            .build()
        return databaseInstance
    }

    @Provides
    fun provideCitiesDao(appDatabase: AppDatabase): CitiesDao {
        return appDatabase.citiesDao
    }

    @Provides
    fun provideWeatherForecastDao(appDatabase: AppDatabase): WeatherForecastDao {
        return appDatabase.weatherForecastDao
    }

}