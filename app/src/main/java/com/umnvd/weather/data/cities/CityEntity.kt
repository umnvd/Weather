package com.umnvd.weather.data.cities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cities")
data class CityEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: Long,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "lat")
    val lat: Float,
    @ColumnInfo(name = "lon")
    val lon: Float,
    @ColumnInfo(name = "position")
    val position: Int,
)
