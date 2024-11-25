package com.marparedes.citieslistapp.domain.usecase.cities

import com.marparedes.citieslistapp.common.Resource
import com.marparedes.citieslistapp.data.model.CityDTO
import com.marparedes.citieslistapp.domain.model.City
import com.marparedes.citieslistapp.domain.repository.CityRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetCitiesUseCase @Inject constructor (private val repository: CityRepository) {
    operator fun invoke(): Flow<Resource<List<City>>> = flow {
        try {
            emit(Resource.Loading)
            emit(Resource.Success(data = repository.getCities()))
        } catch (e: Exception) {
            emit(Resource.Error(errorMessage = e.message.toString()))
        }
    }.flowOn(Dispatchers.IO)
}
