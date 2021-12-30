package com.umnvd.weather.data.cities.cities

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface CitiesDao {

    @Query("SELECT id, name FROM cities ORDER BY position ASC")
    fun getCities(): Flow<List<CitiesListItemTuple>>

    @Query("SELECT * FROM cities WHERE id = :id")
    suspend fun getCityById(id: Long): CityEntity

    @Query("UPDATE cities SET position = :newPosition WHERE position = :oldPosition")
    suspend fun updateCityPosition(oldPosition: Int, newPosition: Int)

    @Transaction
    suspend fun updateCitiesPositions(fromPosition: Int, toPosition: Int) {
        updateCityPosition(fromPosition, -1)
        if (fromPosition < toPosition) {
            for (position in fromPosition + 1 .. toPosition) {
                updateCityPosition(position, position - 1)
            }
        } else {
            for (position in fromPosition - 1 downTo toPosition) {
                updateCityPosition(position, position + 1)
            }
        }
        updateCityPosition(-1, toPosition)
    }

    @Insert
    suspend fun insertCities(cities: List<CityEntity>)

}