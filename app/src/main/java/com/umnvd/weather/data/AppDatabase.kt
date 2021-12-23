package com.umnvd.weather.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.umnvd.weather.data.cities.CitiesDao
import com.umnvd.weather.data.cities.CityEntity
import com.umnvd.weather.data.weather.forecast.WeatherForecastDao
import com.umnvd.weather.data.weather.forecast.WeatherForecastEntity

@Database(
    version = 1,
    entities = [
        CityEntity::class,
        WeatherForecastEntity::class
    ]
)
abstract class AppDatabase: RoomDatabase() {

    abstract val citiesDao: CitiesDao

    abstract val weatherForecastDao: WeatherForecastDao

}