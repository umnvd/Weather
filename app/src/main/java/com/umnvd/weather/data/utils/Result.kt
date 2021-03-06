package com.umnvd.weather.data.utils

import com.umnvd.weather.data.WeatherAppException

typealias Mapper<Input, Output> = (Input) -> Output

sealed class Result<T>(
    val data: T?
) {

    open fun <R> map(mapper: Mapper<T, R>): Result<R> = when (this) {
        is PendingResult -> PendingResult(data?.let { mapper(it) })
        is ErrorResult -> ErrorResult(error, data?.let { mapper(it) })
        is SuccessResult -> SuccessResult(mapper(data!!))
    }

}

sealed class FinalResult<T>(
    data: T?
): Result<T>(data) {

    override fun <R> map(mapper: Mapper<T, R>): FinalResult<R> = super.map(mapper) as FinalResult<R>

}

class PendingResult<T>(data: T? = null) : Result<T>(data)

class ErrorResult<T>(val error: WeatherAppException, data: T? = null) : FinalResult<T>(data)

class SuccessResult<T>(data: T) : FinalResult<T>(data)
