package com.marparedes.citieslistapp.data.model

import com.google.gson.annotations.SerializedName

data class CityDTO(
    @SerializedName("_id")
    val id: String,
    val name: String,
    val country: String,
    val coord: Coordinates
)

data class Coordinates(
    val lat: Double,
    val lon: Double
)