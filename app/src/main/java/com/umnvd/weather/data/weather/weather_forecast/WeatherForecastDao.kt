package com.umnvd.weather.data.weather.weather_forecast

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WeatherForecastDao {

    @Query("SELECT * FROM weather_forecasts WHERE city_id = :cityId")
    suspend fun getWeatherForecast(cityId: Long): List<DayWeatherForecastEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeatherForecast(forecast: List<DayWeatherForecastEntity>)

}