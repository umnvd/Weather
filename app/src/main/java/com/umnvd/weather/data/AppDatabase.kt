package com.umnvd.weather.data

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.umnvd.weather.data.cities.cities.CitiesDao
import com.umnvd.weather.data.cities.cities.CityEntity
import com.umnvd.weather.data.cities.utils.CitiesPrePopulationCallback
import com.umnvd.weather.data.weather.weather_forecast.WeatherForecastDao
import com.umnvd.weather.data.weather.weather_forecast.DayWeatherForecastEntity

@Database(
    version = 1,
    entities = [
        CityEntity::class,
        DayWeatherForecastEntity::class
    ],
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {

    abstract val citiesDao: CitiesDao

    abstract val weatherForecastDao: WeatherForecastDao

}