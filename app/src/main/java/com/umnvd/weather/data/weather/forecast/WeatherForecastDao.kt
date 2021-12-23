package com.umnvd.weather.data.weather.forecast

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface WeatherForecastDao {

    @Query("SELECT * FROM weather_forecasts WHERE city_id = :cityId")
    suspend fun getWeatherForecast(cityId: Long): List<DayWeatherForecastEntity>

    @Insert
    suspend fun insertWeatherForecast(forecast: List<DayWeatherForecastEntity>)

}