package com.marparedes.citieslistapp.data.repository

import com.marparedes.citieslistapp.common.toCity
import com.marparedes.citieslistapp.data.remote.ApiService
import com.marparedes.citieslistapp.domain.model.City
import com.marparedes.citieslistapp.domain.repository.CityRepository
import javax.inject.Inject

class CityRepositoryImpl @Inject constructor(private val apiService: ApiService) : CityRepository {
    override suspend fun getCities(): List<City> {
        return apiService.getCitiesList().map { it.toCity() }
    }

}