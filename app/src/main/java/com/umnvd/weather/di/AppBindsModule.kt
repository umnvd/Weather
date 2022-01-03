package com.umnvd.weather.di

import com.umnvd.weather.data.cities.CitiesRepository
import com.umnvd.weather.data.cities.CitiesRepositoryImpl
import com.umnvd.weather.data.cities.current_city.CurrentCityStore
import com.umnvd.weather.data.cities.current_city.CurrentCityStoreImpl
import com.umnvd.weather.data.weather.current_weather.CurrentWeatherRepository
import com.umnvd.weather.data.weather.current_weather.CurrentWeatherRepositoryImpl
import com.umnvd.weather.data.weather.weather_forecast.WeatherForecastRepository
import com.umnvd.weather.data.weather.weather_forecast.WeatherForecastRepositoryImpl
import dagger.Binds
import dagger.Module

@Suppress("FunctionName")
@Module
interface AppBindsModule {

    @Binds
    fun bindCurrentCityStoreImpl_to_CurrentCityStore(
        currentCityStoreImpl: CurrentCityStoreImpl
    ): CurrentCityStore

    @Binds
    fun bindCitiesRepositoryImpl_to_CitiesRepository(
        citiesRepositoryImpl: CitiesRepositoryImpl
    ): CitiesRepository

    @Binds
    fun bindWeatherForecastRepositoryImpl_to_WeatherForecastRepository(
        weatherForecastRepositoryImpl: WeatherForecastRepositoryImpl
    ): WeatherForecastRepository

    @Binds
    fun bindCurrentWeatherRepositoryImpl_to_CurrentWeatherRepository(
        currentWeatherRepositoryImpl: CurrentWeatherRepositoryImpl
    ): CurrentWeatherRepository
}