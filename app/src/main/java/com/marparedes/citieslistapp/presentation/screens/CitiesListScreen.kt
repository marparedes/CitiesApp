package com.marparedes.citieslistapp.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.marparedes.citieslistapp.domain.model.City
import com.marparedes.citieslistapp.presentation.screens.component.CitiesList
import com.marparedes.citieslistapp.presentation.screens.component.FilterInput
import com.marparedes.citieslistapp.presentation.screens.component.Loading
import com.marparedes.citieslistapp.presentation.screens.component.MapView
import com.marparedes.citieslistapp.presentation.screens.component.isLandscape
import com.marparedes.citieslistapp.presentation.viewmodel.CityListViewModel

@Composable
fun CitiesListScreen(modifier: Modifier = Modifier, navigateToCityMapScreen: (City) -> Unit) {

    val viewModel: CityListViewModel = hiltViewModel()
    val result = viewModel.state.value
    val isLandscape = isLandscape()
    var selectedCity by remember { mutableStateOf<City?>(null) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .imePadding()
    ) {
        if (result.isLoading && result.cities.isEmpty()) {
            Loading()
        }
        if (isLandscape) {
            Row {
                Column(modifier = Modifier.weight(1f)) {
                    FilterInput(
                        modifier = Modifier.padding(end = 16.dp)
                    )

                    CitiesList(
                        cities = result.cities,
                        isLoading = result.isLoading,
                        hasMoreData = viewModel.hasMoreData,
                        loadNextPage = { viewModel.loadNextPage() },
                        onItemClick = { city -> selectedCity = city }
                    )

                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .background(Color.Gray)
                ) {
                    selectedCity?.let { city ->
                        MapView(city = city)
                    } ?: Text(
                        text = "Select a city to see it on the map",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        } else {
            FilterInput(
                modifier = Modifier.fillMaxWidth()
            )
            CitiesList(
                cities = result.cities,
                isLoading = result.isLoading && result.cities.isNotEmpty(),
                hasMoreData = viewModel.hasMoreData,
                loadNextPage = { viewModel.loadNextPage() },
                onItemClick = { selectedCity ->
                    navigateToCityMapScreen(selectedCity)
                }
            )
        }
    }
}

