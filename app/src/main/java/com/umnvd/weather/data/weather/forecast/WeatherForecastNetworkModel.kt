package com.umnvd.weather.data.weather.forecast

import com.google.gson.annotations.SerializedName

data class WeatherForecastNetworkModel(
    @SerializedName("timezone_offset")
    val timezoneOffset: Long,
    @SerializedName("daily")
    val daily: List<Daily>
)

data class Daily(
    @SerializedName("dt")
    val date: Long,
    @SerializedName("sunrise")
    val sunrise: Long,
    @SerializedName("sunset")
    val sunset: Long,
    @SerializedName("temp")
    val temp: Temp,
    @SerializedName("pressure")
    val pressure: Long,
    @SerializedName("humidity")
    val humidity: Long,
    @SerializedName("wind_speed")
    val windSpeed: Double,
    @SerializedName("wind_deg")
    val windDeg: Long,
    @SerializedName("weather")
    val weather: List<Weather>,
    @SerializedName("pop")
    val precipitationProb: Double,
)

data class Temp (
    @SerializedName("day")
    val day: Double,
    @SerializedName("night")
    val night: Double,
    @SerializedName("eve")
    val evening: Double,
    @SerializedName("morn")
    val morning: Double
)

data class Weather (
    @SerializedName("description")
    val description: String,
    @SerializedName("icon")
    val iconId: String
)