package com.umnvd.weather.data.weather.forecast

import androidx.room.Dao
import androidx.room.Query

@Dao
interface WeatherForecastDao {

//    @Query()
    suspend fun getWeatherForecast(cityId: Long): List<WeatherForecastEntity>

//    @Query()
    suspend fun saveWeatherForecast(forecast: List<WeatherForecastEntity>)

}