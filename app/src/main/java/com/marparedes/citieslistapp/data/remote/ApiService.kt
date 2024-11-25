package com.marparedes.citieslistapp.data.remote

import com.marparedes.citieslistapp.data.model.CityDTO
import retrofit2.http.GET

interface ApiService {

    @GET("cities.json")
    suspend fun getCitiesList() : List<CityDTO>
}