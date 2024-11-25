package com.marparedes.citieslistapp.presentation.screens.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.marparedes.citieslistapp.domain.model.City

@Composable
fun CityListItem(item: City, onItemClick: (City) -> Unit, isGray: Boolean) {
    ListItem(
        headlineContent = { Text(text = "${item.name}, ${item.country}") },
        colors = ListItemDefaults.colors(
            containerColor = if (isGray) Color.LightGray else Color.Transparent
        ),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick(item) }
    )
}