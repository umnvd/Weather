package com.umnvd.weather.di

import androidx.lifecycle.ViewModel
import com.umnvd.weather.screens.AssistedViewModelFactory
import com.umnvd.weather.screens.cities.CitiesViewModel
import com.umnvd.weather.screens.weather_forecast.WeatherForecastViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import dagger.multibindings.Multibinds

@Module
interface ViewModelBindsModule {

    @Multibinds
    fun viewModels(): Map<Class<out ViewModel>, @JvmSuppressWildcards ViewModel>

    @Multibinds
    fun assistedFactories(): Map<Class<out ViewModel>,
            @JvmSuppressWildcards AssistedViewModelFactory<out ViewModel>>

    @Binds
    @[IntoMap ViewModelKey(CitiesViewModel::class)]
    fun bindCitiesViewModel(viewModel: CitiesViewModel): ViewModel

    @Binds
    @[IntoMap ViewModelKey(WeatherForecastViewModel::class)]
    fun bindWeatherForecastViewModelFactory(
        factory: WeatherForecastViewModel.Factory
    ): AssistedViewModelFactory<out ViewModel>

}