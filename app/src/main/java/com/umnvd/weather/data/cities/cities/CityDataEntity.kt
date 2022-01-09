package com.umnvd.weather.data.cities.cities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "city_data")
data class CityDataEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: Long,
    @ColumnInfo(name = "lat")
    val lat: Float,
    @ColumnInfo(name = "lon")
    val lon: Float,
    @ColumnInfo(name = "position")
    val position: Int
)