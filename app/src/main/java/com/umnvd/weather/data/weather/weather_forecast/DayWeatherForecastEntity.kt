package com.umnvd.weather.data.weather.weather_forecast

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import com.umnvd.weather.data.cities.cities.CityEntity

@Entity(
    tableName = "weather_forecasts",
    primaryKeys = ["city_id", "day_id"],
    foreignKeys = [
        ForeignKey(
            entity = CityEntity::class,
            parentColumns = ["id"],
            childColumns = ["city_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class DayWeatherForecastEntity(
    @ColumnInfo(name = "city_id")
    val cityId: Long,
    @ColumnInfo(name = "day_id")
    val dayId: Long,
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
    @ColumnInfo(name = "wind_speed")
    val windSpeed: Int,
    @ColumnInfo(name = "wind_deg")
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
) {



}