package com.umnvd.weather.di

import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module(
    includes = [
        AppBindsModule::class,
        DatabaseModule::class,
        NetworkModule::class,
        ViewModelBindsModule::class,
        UiModule::class
    ]
)
class AppModule {

    @Provides
    @IO
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @Default
    fun provideDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

}