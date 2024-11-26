package com.marparedes.citieslistapp.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marparedes.citieslistapp.common.Resource
import com.marparedes.citieslistapp.domain.model.City
import com.marparedes.citieslistapp.domain.usecase.cities.GetCitiesUseCase
import com.marparedes.citieslistapp.presentation.model.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CityListViewModel @Inject constructor(private val getCitiesUseCase: GetCitiesUseCase): ViewModel() {
    private val _state = mutableStateOf(UiState())
    val state : State<UiState> get() = _state
    private var allCities = listOf<City>()
    private var pageSize = 15
    private var currentPage = 0
    var hasMoreData = false

    init {
        getCities()
    }

    private fun getCities() {
        getCitiesUseCase().onEach { result ->
            when (result) {
                is Resource.Loading -> _state.value = UiState(isLoading = true)
                is Resource.Success -> {
                    result.data.let {
                        allCities = it.sortedBy { city -> city.name }
                        hasMoreData = it.size > pageSize
                        loadNextPage(isFirstLoad = true)
                    }
                }
                is Resource.Error -> _state.value = UiState(error = result.errorMessage)
            }
        }.launchIn(viewModelScope)
    }

    fun loadNextPage(isFirstLoad: Boolean = false) {
        if (_state.value.isLoading && !isFirstLoad) return

        if (_state.value.query.isNotEmpty()) return

        val start = currentPage * pageSize
        val end = (currentPage + 1) * pageSize


        if (start >= allCities.size) {
            hasMoreData = false
            return
        }

        _state.value = _state.value.copy(isLoading = true)
        val nextCities = allCities.subList(start, end.coerceAtMost(allCities.size))

        _state.value = if (isFirstLoad) {
            _state.value.copy(
                cities = nextCities,
                isLoading = false
            )
        } else {
            _state.value.copy(
                cities = _state.value.cities + nextCities,
                isLoading = false
            )
        }

        hasMoreData = end < allCities.size
        currentPage++
    }

    fun filterCities(query: String) {
        _state.value = _state.value.copy(query = query)
        val filtered = if (query.isEmpty()) {
            allCities
        } else {
            allCities.filter { it.name.startsWith(query, ignoreCase = true) }
        }

        _state.value = _state.value.copy(cities = filtered)
    }

    fun getCityById(id: String?): City? {
        return allCities.find { it.id == id }
    }
}