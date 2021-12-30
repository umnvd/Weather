package com.umnvd.weather.data.utils

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import java.io.IOException
import java.lang.NullPointerException

inline fun <Local, Remote> networkBoundResult(
    crossinline fetchFromLocal: suspend () -> Local,
    crossinline shouldFetchFromRemote: (Local) -> Boolean,
    crossinline fetchFromRemote: suspend () -> Response<Remote>,
    crossinline saveRemoteData: suspend (Remote) -> Unit,
): Flow<Result<Local>> = flow {
    emit(PendingResult())

    val localData = try {
        fetchFromLocal()
    } catch (e: NullPointerException) {
        null
    }

    if (localData == null || shouldFetchFromRemote(localData)) {
        emit(PendingResult(localData))

        when (val remoteData = fetchFromRemote().takeResult()) {
            is SuccessResult -> {
                saveRemoteData(remoteData.data!!)
                emit(SuccessResult(fetchFromLocal()))
            }
            is ErrorResult -> {
                emit(ErrorResult(remoteData.message))
            }
        }

    } else {
        emit(SuccessResult(localData))
    }
}