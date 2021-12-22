package com.umnvd.weather.data.util

import retrofit2.Response

fun <T> Response<T>.toFinalResult(): FinalResult<T> {
    return if (this.isSuccessful) {
        val body = this.body()

        if (body == null || this.code() == 204) {
            ErrorResult("Empty response")
        } else {
            SuccessResult(body)
        }

    } else {
        ErrorResult(this.message() ?: "Unknown error")
    }
}