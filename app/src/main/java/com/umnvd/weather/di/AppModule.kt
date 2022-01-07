package com.umnvd.weather.di

import android.app.Application
import android.content.Context
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
        WorkManagerBindsModule::class
    ]
)
class AppModule {

    @Provides
    fun provideContext(application: Application): Context = application.applicationContext

    @Provides
    @InputOutput
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @Computation
    fun provideDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

}