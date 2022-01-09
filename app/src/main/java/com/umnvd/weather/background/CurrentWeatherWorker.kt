package com.umnvd.weather.background

import android.content.Context
import androidx.work.*
import com.umnvd.weather.data.utils.ErrorResult
import com.umnvd.weather.data.weather.current_weather.CurrentWeatherNotifications
import com.umnvd.weather.data.weather.current_weather.CurrentWeatherRepository
import com.umnvd.weather.di.AssistedWorkerFactory
import com.umnvd.weather.di.InputOutput
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit

class CurrentWeatherWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParameters: WorkerParameters,
    private val currentWeatherRepository: CurrentWeatherRepository,
    private val currentWeatherNotifications: CurrentWeatherNotifications,
    @InputOutput private val ioDispatcher: CoroutineDispatcher
): CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        val result = withContext(ioDispatcher) {
            currentWeatherRepository.getCurrentWeather()
        }
        if (result is ErrorResult || result.data == null) return Result.failure()
        currentWeatherNotifications.show(result.data)
        return Result.success()
    }

    @AssistedFactory
    interface Factory: AssistedWorkerFactory<CurrentWeatherWorker> {

        override fun create(
            context: Context,
            workerParameters: WorkerParameters
        ): CurrentWeatherWorker

    }

    companion object {

        const val UNIQUE_WORK_NAME = "currentWeatherWork"

        private const val REPEAT = 60L
        private const val FLEX = 5L

        fun buildRequest(): PeriodicWorkRequest {
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

            return PeriodicWorkRequestBuilder<CurrentWeatherWorker>(
                15, TimeUnit.MINUTES,
                FLEX, TimeUnit.MINUTES
            )
                .setConstraints(constraints)
                .build()
        }

    }

}