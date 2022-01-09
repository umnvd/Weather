package com.umnvd.weather.data.utils

import android.util.Log
import com.umnvd.weather.data.NetworkException
import retrofit2.Response

fun <T> Response<T>.takeResult(): FinalResult<T> {
    return if (this.isSuccessful) {
        val body = this.body()

        if (body == null || this.code() == 204) {
            ErrorResult(NetworkException())
        } else {
            SuccessResult(body)
        }

    } else {
        Log.d("Utils", "Error: ${this.code()}")
        ErrorResult(NetworkException())
    }
}