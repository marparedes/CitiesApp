package com.marparedes.citieslistapp.domain.model

import com.marparedes.citieslistapp.data.model.Coordinates

data class City(
    val id: String,
    val name: String,
    val country: String,
    val coord: Coordinates
)

