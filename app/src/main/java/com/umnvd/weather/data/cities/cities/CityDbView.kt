package com.umnvd.weather.data.cities.cities

import androidx.room.ColumnInfo
import androidx.room.DatabaseView

@DatabaseView(
    viewName = "city_view",
    value = "SELECT" +
            " city_data.id as id, " +
            " city_names.name as name, " +
            " city_names.language as language, " +
            " city_data.lat as lat, " +
            " city_data.lon as lon, " +
            " city_data.position as position " +
            "FROM city_data " +
            "JOIN city_names " +
            "ON city_data.id = city_names.id"
)
data class CityDbView(
    @ColumnInfo(name = "id")
    val id: Long,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "language")
    val language: String,
    @ColumnInfo(name = "lat")
    val lat: Float,
    @ColumnInfo(name = "lon")
    val lon: Float,
    @ColumnInfo(name = "position")
    val position: Int
)
