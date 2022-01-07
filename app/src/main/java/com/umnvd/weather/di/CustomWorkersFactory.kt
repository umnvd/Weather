package com.umnvd.weather.di

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import javax.inject.Inject


interface AssistedWorkerFactory<W : ListenableWorker> {
    fun create(context: Context, workerParameters: WorkerParameters): W
}

class CustomWorkersFactory @Inject constructor(
    private val assistedFactories: Map<Class<out ListenableWorker>,
            @JvmSuppressWildcards AssistedWorkerFactory<out ListenableWorker>>
): WorkerFactory() {

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        return assistedFactories
            .asIterable()
            .firstOrNull { workerClassName == it.key.name }
            ?.value
            ?.create(appContext, workerParameters)
    }

}