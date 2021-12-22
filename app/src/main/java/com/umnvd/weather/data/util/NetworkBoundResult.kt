package com.umnvd.weather.data.util

import kotlinx.coroutines.flow.flow
import retrofit2.Response
import java.lang.Exception

inline fun <Local, Remote> networkBoundResult(
    crossinline fetchFromLocal: suspend () -> Local,
    crossinline shouldFetchFromRemote: (Local) -> Boolean,
    crossinline fetchFromRemote: suspend () -> Response<Remote>,
    crossinline remoteToLocalMapper: (Remote) -> Local,
    crossinline saveLocalData: suspend (Local) -> Unit,
): FlowResult<Local> = flow {
    emit(PendingResult())

    val localData = try {
        fetchFromLocal().also { emit(PendingResult(it)) }
    } catch (e: Exception) {
        null
    }

    if (localData == null || shouldFetchFromRemote(localData)) {
        when (val remoteData = fetchFromRemote().toFinalResult()) {
            is SuccessResult -> {
                saveLocalData(remoteToLocalMapper(remoteData.data!!))
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