package com.marparedes.citieslistapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.marparedes.citieslistapp.presentation.screens.CitiesListScreen
import com.marparedes.citieslistapp.presentation.screens.component.MapView
import com.marparedes.citieslistapp.presentation.viewmodel.CityListViewModel

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = "citiesList"
) {
    val viewModel: CityListViewModel = hiltViewModel()

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable("citiesList") {
            CitiesListScreen(
                modifier = modifier,
                navigateToCityMapScreen = { city ->
                    navController.navigate("map/${city.id}")
                }
            )
        }

        composable("map/{cityId}") { backStackEntry ->
            val cityId = backStackEntry.arguments?.getString("cityId")
            val city = viewModel.getCityById(cityId)
            city?.let {
                MapView(city = it, showToolbar = true, onBack = { navController.popBackStack() })
            }
        }
    }

}