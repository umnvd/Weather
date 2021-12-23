package com.umnvd.weather.data.weather

import com.umnvd.weather.data.weather.current.CurrentWeatherNetworkModel
import com.umnvd.weather.data.weather.forecast.WeatherForecastNetworkModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("data/2.5/onecall?exclude=current,minutely,hourly,alerts")
    suspend fun getWeatherForecasts(
        @Query("lat") cityLat: Float,
        @Query("lon") cityLon: Float
    ): Response<WeatherForecastNetworkModel>

    @GET("data/2.5/weather")
    suspend fun getCurrentWeather(
        @Query("id") cityId: Long
    ): Response<CurrentWeatherNetworkModel>

}