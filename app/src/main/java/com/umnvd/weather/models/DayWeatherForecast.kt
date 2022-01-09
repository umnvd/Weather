package com.umnvd.weather.models

import androidx.annotation.StringRes
import com.umnvd.weather.R
import java.util.*

data class DayWeatherForecast(
    val date: Date,
    val iconUrl: String,
    val description: String,
    val humidity: Int,
    val pressure: Int,
    val windSpeed: Int,
    val windDir: WindDirection,
    val precipitationProb: Int,
    val dayTemp: Int,
    val nightTemp: Int,
    val eveningTemp: Int,
    val morningTemp: Int,
    val sunrise: Date,
    val sunset: Date,
)

enum class WindDirection(
    val degreeRange: IntRange,
    @StringRes val nameId: Int
) {
    N(0..25, R.string.direction_n),
    NE(25 until 65, R.string.direction_ne),
    E(65 until 115, R.string.direction_e),
    SE(115 until 155, R.string.direction_se),
    S(155 until 205, R.string.direction_s),
    SW(205 until 245, R.string.direction_sw),
    W(245 until 295, R.string.direction_w),
    NW(295 until 335, R.string.direction_nw),
}

fun Int.toWindDirection(): WindDirection {
    return when(this) {
        in WindDirection.NE.degreeRange -> WindDirection.NE
        in WindDirection.E.degreeRange -> WindDirection.E
        in WindDirection.SE.degreeRange -> WindDirection.SE
        in WindDirection.S.degreeRange -> WindDirection.S
        in WindDirection.SW.degreeRange -> WindDirection.SW
        in WindDirection.W.degreeRange -> WindDirection.W
        in WindDirection.NW.degreeRange -> WindDirection.NW
        else -> WindDirection.N
    }
}
