package com.umnvd.weather.di

import androidx.work.ListenableWorker
import com.umnvd.weather.background.CurrentWeatherWorker
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import dagger.multibindings.Multibinds

@Module
interface WorkManagerBindsModule {

    @Multibinds
    fun assistedWorkerFactories(): Map<Class<out ListenableWorker>,
            @JvmSuppressWildcards AssistedWorkerFactory<out ListenableWorker>>

    @Binds
    @[IntoMap WorkerKey(CurrentWeatherWorker::class)]
    fun bindCurrentWeatherWorkerFactory(
        factory: CurrentWeatherWorker.Factory
    ): AssistedWorkerFactory<out ListenableWorker>

}