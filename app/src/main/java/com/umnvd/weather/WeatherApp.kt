package com.umnvd.weather

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.work.Configuration
import androidx.work.DelegatingWorkerFactory
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

}

val Context.appComponent: AppComponent
    get() = when (this) {
        is WeatherApp -> appComponent
        else -> this.applicationContext.appComponent
    }