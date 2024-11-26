package com.marparedes.citieslistapp.presentation.screens.component

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.AdvancedMarker
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.marparedes.citieslistapp.domain.model.City

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnrememberedMutableState")
@Composable
fun MapView(city: City, showToolbar: Boolean = false, onBack: () -> Unit = {}) {

    val cityCoords = LatLng(city.coord.lat, city.coord.lon)

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(cityCoords, 10f)
    }
    LaunchedEffect(cityCoords) {
        cameraPositionState.animate(CameraUpdateFactory.newLatLngZoom(cityCoords, 10f))
    }

    Column(modifier = Modifier.fillMaxSize()) {
        if (showToolbar) {
            TopAppBar(
                title = { Text(text = "Back") },
                navigationIcon = {
                    IconButton(onClick = { onBack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    titleContentColor = Color.Black,
                    navigationIconContentColor = Color.Black
                )
            )
        }


        Box(modifier = Modifier.fillMaxSize()) {
            GoogleMap(
                cameraPositionState = cameraPositionState
            ) {
                AdvancedMarker(
                    state = MarkerState(position = cityCoords),
                    title = city.name
                )
            }
        }
    }
}