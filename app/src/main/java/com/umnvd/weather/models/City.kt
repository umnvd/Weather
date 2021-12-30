package com.umnvd.weather.models

data class City(
    val id: Long,
    val name: String,
    val lat: Float,
    val lon: Float,
    val isCurrent: Boolean
)

data class CitiesListItem(
    val id: Long,
    val name: String,
    val isCurrent: Boolean
)
