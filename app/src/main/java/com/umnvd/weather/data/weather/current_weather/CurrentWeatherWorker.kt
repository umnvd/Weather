package com.umnvd.weather.data.weather.current_weather

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.umnvd.weather.di.AssistedWorkerFactory
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class CurrentWeatherWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParameters: WorkerParameters,
    private val currentWeatherRepository: CurrentWeatherRepository,
): CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        TODO("Not yet implemented")
    }

    @AssistedFactory
    interface Factory: AssistedWorkerFactory<CurrentWeatherWorker> {

        override fun create(
            context: Context,
            workerParameters: WorkerParameters
        ): CurrentWeatherWorker

    }

}