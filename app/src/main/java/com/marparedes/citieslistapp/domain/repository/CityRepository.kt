package com.marparedes.citieslistapp.domain.repository

import com.marparedes.citieslistapp.data.model.CityResponse
import com.marparedes.citieslistapp.domain.model.City

interface CityRepository {

    suspend fun getCities(): List<City>
}