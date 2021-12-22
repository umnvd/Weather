package com.umnvd.weather.data.util

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import java.lang.IllegalArgumentException

typealias FlowResult<T> = Flow<Result<T>>
typealias FlowFinalResult<T> = Flow<FinalResult<T>>
typealias LiveResult<T> = LiveData<Result<T>>

typealias Mapper<Input, Output> = (Input) -> Output

sealed class Result<T> {

    open fun <R> map(mapper: Mapper<T, R>): Result<R> = when (this) {
        is PendingResult -> PendingResult(data?.let { mapper(it) })
        is ErrorResult -> ErrorResult(message, data?.let { mapper(it) })
        is SuccessResult -> SuccessResult(mapper(data))
    }

}

sealed class FinalResult<T>: Result<T>() {

    override fun <R> map(mapper: Mapper<T, R>): FinalResult<R> = super.map(mapper) as FinalResult<R>

}

class PendingResult<T>(val data: T? = null) : Result<T>()

class ErrorResult<T>(val message: String, val data: T? = null) : FinalResult<T>()

class SuccessResult<T>(val data: T) : FinalResult<T>()
