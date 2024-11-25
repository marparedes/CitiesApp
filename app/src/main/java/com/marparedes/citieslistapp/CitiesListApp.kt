package com.marparedes.citieslistapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CitiesListApp : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}