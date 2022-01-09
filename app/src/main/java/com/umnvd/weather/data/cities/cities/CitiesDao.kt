package com.umnvd.weather.data.cities.cities

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CitiesDao {

    @Query("SELECT * FROM city_view WHERE language = :language ORDER BY position")
    fun getCitiesListItemTuples(language: String): Flow<List<CityDbView>>

    @Query("SELECT * FROM city_view WHERE id = :id AND language = :language")
    suspend fun getCityTupleById(id: Long, language: String): CityDbView

    @Query("UPDATE city_data SET position = :newPosition WHERE position = :oldPosition")
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

}