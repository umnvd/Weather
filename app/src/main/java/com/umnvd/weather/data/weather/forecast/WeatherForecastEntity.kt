package com.umnvd.weather.data.weather.forecast

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_forecasts")
data class WeatherForecastEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = -1,
    @ColumnInfo(name = "city_id")
    val cityId: Long,
    @ColumnInfo(name = "date")
    val date: Long,
    @ColumnInfo(name = "icon_url")
    val iconUrl: String,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "humidity")
    val humidity: Int,
    @ColumnInfo(name = "pressure")
    val pressure: Int,
    @ColumnInfo(name = "wind")
    val windSpeed: Int,
    @ColumnInfo(name = "wind_dir")
    val windDeg: Int,
    @ColumnInfo(name = "precipitation_prob")
    val precipitationProb: Int,
    @ColumnInfo(name = "day_temp")
    val dayTemp: Int,
    @ColumnInfo(name = "night_temp")
    val nightTemp: Int,
    @ColumnInfo(name = "evening_temp")
    val eveningTemp: Int,
    @ColumnInfo(name = "morning_temp")
    val morningTemp: Int,
    @ColumnInfo(name = "sunrise")
    val sunrise: Long,
    @ColumnInfo(name = "sunset")
    val sunset: Long,
    @ColumnInfo(name = "updated_at")
    val updatedAt: Long
)