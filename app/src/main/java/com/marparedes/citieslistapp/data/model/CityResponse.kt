package com.marparedes.citieslistapp.data.model

import com.google.gson.annotations.SerializedName

data class CityResponse(
    @SerializedName("data")
    val data: List<CityDTO>?,
    @SerializedName("status")
    val status: Int?
)
