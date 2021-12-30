package com.umnvd.weather.data.utils

import retrofit2.Response

fun <T> Response<T>.takeResult(): FinalResult<T> {
    return if (this.isSuccessful) {
        val body = this.body()

        if (body == null || this.code() == 204) {
            ErrorResult("Empty body")
        } else {
            SuccessResult(body)
        }

    } else {
        ErrorResult(this.message() ?: "Unknown error")
    }
}