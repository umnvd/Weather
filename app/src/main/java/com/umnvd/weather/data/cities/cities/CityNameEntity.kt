package com.umnvd.weather.data.cities.cities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(tableName = "city_names",
    primaryKeys = ["id", "language"],
    foreignKeys = [
        ForeignKey(
            entity = CityDataEntity::class,
            parentColumns = ["id"],
            childColumns = ["id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class CityNameEntity(
    @ColumnInfo(name = "id")
    val id: Long,
    @ColumnInfo(name = "language")
    val language: String,
    @ColumnInfo(name = "name")
    val name: String
)