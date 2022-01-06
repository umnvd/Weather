package com.umnvd.weather.data.weather

import com.umnvd.weather.data.weather.current_weather.CurrentWeatherNetworkModel
import com.umnvd.weather.data.weather.weather_forecast.WeatherForecastNetworkModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

const val ICON_BASE_URL = "https://openweathermap.org/img/wn/%s@2x.png"

interface WeatherApiService {

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