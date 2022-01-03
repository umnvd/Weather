package com.umnvd.weather.data.cities.utils

import android.util.JsonReader

class JsonCitiesService {

    fun readJsonCities(reader: JsonReader): List<JsonCity> {
        val jsonCities = mutableListOf<JsonCity>()
        reader.beginArray()
        while (reader.hasNext()) {
            jsonCities.add(readJsonCity(reader))
        }
        reader.endArray()
        return jsonCities.toList()
    }

    private fun readJsonCity(reader: JsonReader): JsonCity {
        var id = -1L
        var name = ""
        var country = ""
        var coord = JsonCoord(1.0, 1.0)

        reader.beginObject()
        while (reader.hasNext()) {
            when (reader.nextName()) {
                "id" -> id = reader.nextLong()
                "name" -> name = reader.nextString()
                "state" -> reader.skipValue()
                "country" -> country = reader.nextString()
                "coord" -> coord = readJsonCoord(reader)
            }
        }
        reader.endObject()

        return JsonCity(id, name, country, coord)
    }

    private fun readJsonCoord(reader: JsonReader): JsonCoord {
        var lon = 1.0
        var lat = 1.0

        reader.beginObject()
        while (reader.hasNext()) {
            when (reader.nextName()) {
                "lon" -> lon = reader.nextDouble()
                "lat" -> lat = reader.nextDouble()
            }
        }
        reader.endObject()

        return JsonCoord(lon, lat)
    }

}

data class JsonCity(
    val id: Long,
    val name: String,
    val country: String,
    val coord: JsonCoord,
)

data class JsonCoord(
    val lon: Double,
    val lat: Double
)