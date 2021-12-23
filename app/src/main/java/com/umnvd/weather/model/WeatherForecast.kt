package com.umnvd.weather.model

import java.util.*

data class WeatherForecast(
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

enum class WindDirection(val degreeRange: IntRange) {
    N(0..25), NE(25 until 65),
    E(65 until 115), SE(115 until 155),
    S(155 until 205), SW(205 until 245),
    W(245 until 295), NW(295 until 335),
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
