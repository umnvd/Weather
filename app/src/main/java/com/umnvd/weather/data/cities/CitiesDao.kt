package com.umnvd.weather.data.cities

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface CitiesDao {

    @Query("SELECT id, name FROM cities ORDER BY position ASC")
    fun getCities(): Flow<List<CitiesListItemTuple>>

    @Query("SELECT * FROM cities WHERE id = :id")
    suspend fun getCity(id: Long): CityEntity

    @Query("UPDATE cities SET position = :newPosition WHERE position = :oldPosition")
    suspend fun updatePosition(oldPosition: Int, newPosition: Int)

    @Transaction
    suspend fun updatePositions(fromPosition: Int, toPosition: Int) {
        updatePosition(fromPosition, -1)
        if (fromPosition < toPosition) {
            for (position in fromPosition + 1 .. toPosition) {
                updatePosition(position, position - 1)
            }
        } else {
            for (position in fromPosition - 1 downTo toPosition) {
                updatePosition(position, position + 1)
            }
        }
        updatePosition(-1, toPosition)
    }

}