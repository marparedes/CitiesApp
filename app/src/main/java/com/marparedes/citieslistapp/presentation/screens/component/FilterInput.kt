package com.marparedes.citieslistapp.presentation.screens.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.marparedes.citieslistapp.presentation.viewmodel.CityListViewModel

@Composable
fun FilterInput(
    modifier: Modifier = Modifier,
    viewModel: CityListViewModel
) {
    var query by remember { mutableStateOf("") }
    Column(modifier = modifier) {
        // Buscador
        OutlinedTextField(
            value = query,
            onValueChange = {
                query = it
                viewModel.filterCities(query)
            },
            label = {Text("Filtrar")},
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}