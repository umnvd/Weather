package com.umnvd.weather.di

import android.app.Application
import android.content.Context
import com.umnvd.weather.WeatherApp
import com.umnvd.weather.screens.cities.CitiesFragment
import com.umnvd.weather.screens.weather_forecast.WeatherForecastFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(modules = [AppModule::class])
@Singleton
interface AppComponent {

    fun inject(citiesFragment: CitiesFragment)
    fun inject(weatherForecastFragment: WeatherForecastFragment)
    fun inject(weatherApp: WeatherApp)

    @Component.Factory
    interface Factory {

        fun create(
            @BindsInstance
            application: Application
        ): AppComponent

    }

}