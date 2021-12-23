package com.umnvd.weather.data

import com.umnvd.weather.model.CitiesListItem
import com.umnvd.weather.model.City
import kotlinx.coroutines.flow.Flow


interface CitiesRepository {

    fun getCities(): Flow<List<CitiesListItem>>

    fun getCity(id: Long): Flow<City>

    suspend fun moveCity(fromPosition: Int, toPosition: Int)

    suspend fun changeCurrentCity(newId: Long): Long

    suspend fun getCurrentCity(): City
}