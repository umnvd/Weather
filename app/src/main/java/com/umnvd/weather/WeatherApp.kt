package com.umnvd.weather

import android.app.Application
import android.content.Context
import com.umnvd.weather.di.AppComponent
import com.umnvd.weather.di.DaggerAppComponent

class WeatherApp : Application() {

    lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .context(context = this)
            .build()
    }

}

val Context.appComponent: AppComponent
    get() = when (this) {
        is WeatherApp -> appComponent
        else -> this.applicationContext.appComponent
    }