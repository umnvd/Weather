package com.umnvd.weather.data.util

import kotlinx.coroutines.CoroutineDispatcher

class IoDispatcher(
    val value: CoroutineDispatcher
)

class DefaultDispatcher(
    val value: CoroutineDispatcher
)