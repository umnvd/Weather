package com.umnvd.weather

import android.app.Application
import android.content.Context
import androidx.work.Configuration
import androidx.work.DelegatingWorkerFactory
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.WorkManager
import com.umnvd.weather.background.CurrentWeatherWorker
import com.umnvd.weather.di.AppComponent
import com.umnvd.weather.di.CustomWorkersFactory
import com.umnvd.weather.di.DaggerAppComponent
import javax.inject.Inject

class WeatherApp : Application(), Configuration.Provider {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent
            .factory()
            .create(this)
    }

    @Inject
    lateinit var customWorkersFactory: CustomWorkersFactory

    override fun onCreate() {
        super.onCreate()
        appComponent.inject(this)
        enqueueCurrentWeatherWork()
    }

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .setWorkerFactory(
                DelegatingWorkerFactory().apply {
                    addFactory(customWorkersFactory)
                }
            )
            .build()
    }

    private fun enqueueCurrentWeatherWork() {
        WorkManager.getInstance(this)
            .enqueueUniquePeriodicWork(
                CurrentWeatherWorker.UNIQUE_WORK_NAME,
                ExistingPeriodicWorkPolicy.KEEP,
                CurrentWeatherWorker.buildRequest()
            )
    }

}

val Context.appComponent: AppComponent
    get() = when (this) {
        is WeatherApp -> appComponent
        else -> this.applicationContext.appComponent
    }