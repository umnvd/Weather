package com.umnvd.weather.utils

import androidx.annotation.StringRes

data class MessageConfig(
    @StringRes val message: Int,
    val actionConfig: ActionConfig? = null,
    val isInfinite: Boolean = false
)

data class ActionConfig(
    @StringRes val title: Int,
    val action: () -> Unit
)