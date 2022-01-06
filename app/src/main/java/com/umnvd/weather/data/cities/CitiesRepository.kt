package com.umnvd.weather.data.cities

import com.umnvd.weather.models.CitiesListItem
import com.umnvd.weather.models.City
import kotlinx.coroutines.flow.Flow


interface CitiesRepository {

    fun getCities(): Flow<List<CitiesListItem>>

    suspend fun moveCity(fromPosition: Int, toPosition: Int)

    fun isCityCurrent(cityId: Long): Flow<Boolean>

    suspend fun changeCurrentCity(id: Long)

    suspend fun getCity(id: Long): City

    suspend fun getCurrentCity(): City

}