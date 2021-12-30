package com.umnvd.weather.data.cities

import com.umnvd.weather.models.CitiesListItem
import com.umnvd.weather.models.City
import kotlinx.coroutines.flow.Flow


interface CitiesRepository {

    fun getCities(): Flow<List<CitiesListItem>>

    fun getCity(id: Long): Flow<City>

    suspend fun moveCity(fromPosition: Int, toPosition: Int)

    suspend fun changeCurrentCity(id: Long)

    suspend fun getCurrentCity(): City

}