package com.marparedes.citieslistapp.presentation.model

import com.marparedes.citieslistapp.domain.model.City

data class UiState(
    var isLoading: Boolean = false,
    var error: String = "",
    var data: List<City> = emptyList()
)