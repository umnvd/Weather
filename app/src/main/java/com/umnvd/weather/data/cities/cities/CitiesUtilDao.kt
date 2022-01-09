package com.umnvd.weather.data.cities.cities

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy

@Dao
interface CitiesUtilDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCitiesData(citiesData: List<CityDataEntity>): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCitiesNames(citiesNames: List<CityNameEntity>)

}