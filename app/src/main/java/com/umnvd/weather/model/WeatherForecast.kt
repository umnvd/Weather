package com.umnvd.weather.model

import androidx.annotation.FloatRange
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
    N(0..15), NNE(15 until 35),
    NE(35 until 55), ENE(55 until 75),
    E(75 until 105), ESE(105 until 125),
    SE(125 until 145), SSE(145 until 165),
    S(165 until 195), SSW(195 until 215),
    SW(215 until 235), WSW(235 until 255),
    W(255 until 285), WNW(285 until 305),
    NW(305 until 325), NNW(325 until 345);
}

fun Int.toWindDirection(): WindDirection {
    return when(this) {
        in WindDirection.NNE.degreeRange -> WindDirection.NNE
        in WindDirection.NE.degreeRange -> WindDirection.NE
        in WindDirection.ENE.degreeRange -> WindDirection.ENE

        in WindDirection.E.degreeRange -> WindDirection.E
        in WindDirection.ESE.degreeRange -> WindDirection.ESE
        in WindDirection.SE.degreeRange -> WindDirection.SE
        in WindDirection.SSE.degreeRange -> WindDirection.SSE

        in WindDirection.S.degreeRange -> WindDirection.S
        in WindDirection.SSW.degreeRange -> WindDirection.SSW
        in WindDirection.SW.degreeRange -> WindDirection.SW
        in WindDirection.WSW.degreeRange -> WindDirection.WSW

        in WindDirection.W.degreeRange -> WindDirection.W
        in WindDirection.WNW.degreeRange -> WindDirection.WNW
        in WindDirection.NW.degreeRange -> WindDirection.NW
        in WindDirection.NNW.degreeRange -> WindDirection.NNW

        else -> WindDirection.N
    }
}
