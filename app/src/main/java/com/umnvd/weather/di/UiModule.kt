package com.umnvd.weather.di

import androidx.lifecycle.ViewModel
import com.umnvd.weather.screens.AssistedViewModelFactory
import dagger.Module
import dagger.multibindings.Multibinds

@Module
interface UiModule {

    @Multibinds
    fun viewModels(): Map<Class<out ViewModel>, @JvmSuppressWildcards ViewModel>

    @Multibinds
    fun assistedFactories(): Map<Class<out ViewModel>,
            @JvmSuppressWildcards AssistedViewModelFactory<out ViewModel>>

}