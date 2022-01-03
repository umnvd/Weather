package com.umnvd.weather

import android.app.Application
import android.content.Context
import com.umnvd.weather.di.AppComponent
import com.umnvd.weather.di.DaggerAppComponent

class WeatherApp : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent
            .factory()
            .create(this)
    }

    override fun onCreate() {
        super.onCreate()
        appComponent.inject(this)
    }

}

val Context.appComponent: AppComponent
    get() = when (this) {
        is WeatherApp -> appComponent
        else -> this.applicationContext.appComponent
    }