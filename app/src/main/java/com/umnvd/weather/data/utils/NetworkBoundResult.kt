package com.umnvd.weather.data.utils

import com.umnvd.weather.data.InternetConnectionException
import com.umnvd.weather.data.StorageException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import java.io.IOException

inline fun <Local, Remote> networkBoundResult(
    crossinline fetchFromLocal: suspend () -> Local,
    crossinline shouldFetchFromRemote: (Local) -> Boolean,
    crossinline fetchFromRemote: suspend () -> Response<Remote>,
    crossinline saveRemoteData: suspend (Remote) -> Unit,
): Flow<Result<Local>> = flow {
    emit(PendingResult())

    val localData = try {
        fetchFromLocal()
    } catch (e: Exception) {
        emit(ErrorResult(StorageException().apply { initCause(e) }))
        null
    }

    if (localData == null || shouldFetchFromRemote(localData)) {
        emit(PendingResult(localData))

        try {
            when (val remoteData = fetchFromRemote().takeResult()) {
                is SuccessResult -> {
                    saveRemoteData(remoteData.data!!)
                    emit(SuccessResult(fetchFromLocal()))
                }
                is ErrorResult -> {
                    emit(ErrorResult(remoteData.error, localData))
                }
            }
        } catch (e: IOException) {
            emit(ErrorResult(
                InternetConnectionException().apply { initCause(e) },
                localData
            ))
        }

    } else {
        emit(SuccessResult(localData))
    }
}