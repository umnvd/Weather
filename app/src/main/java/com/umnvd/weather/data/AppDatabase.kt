package com.umnvd.weather.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.umnvd.weather.data.cities.cities.CitiesDao
import com.umnvd.weather.data.cities.cities.CityDataEntity
import com.umnvd.weather.data.cities.cities.CityNameEntity
import com.umnvd.weather.data.cities.cities.CityDbView
import com.umnvd.weather.data.cities.cities.CitiesUtilDao
import com.umnvd.weather.data.weather.weather_forecast.DayWeatherForecastEntity
import com.umnvd.weather.data.weather.weather_forecast.WeatherForecastDao

@Database(
    version = 2,
    entities = [
        CityDataEntity::class,
        CityNameEntity::class,
        DayWeatherForecastEntity::class
    ],
    views = [
        CityDbView::class
    ],
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract val citiesDao: CitiesDao
    abstract val weatherForecastDao: WeatherForecastDao
    abstract val citiesUtilDao: CitiesUtilDao

}