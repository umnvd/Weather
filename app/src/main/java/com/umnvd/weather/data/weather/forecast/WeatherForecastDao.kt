package com.umnvd.weather.data.weather.forecast

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherForecastDao {

    @Query("SELECT * FROM weather_forecasts WHERE city_id = :cityId")
    suspend fun getWeatherForecasts(cityId: Long): List<WeatherForecastEntity>

    @Insert
    suspend fun insertWeatherForecasts(forecast: List<WeatherForecastEntity>)

}