package com.marparedes.citieslistapp.di

import com.marparedes.citieslistapp.common.Constants.BASE_URL
import com.marparedes.citieslistapp.data.remote.ApiService
import com.marparedes.citieslistapp.data.repository.CityRepositoryImpl
import com.marparedes.citieslistapp.domain.repository.CityRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit() = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiService::class.java)

    @Singleton
    @Provides
    fun provideCityRepository(apiService: ApiService): CityRepository = CityRepositoryImpl(apiService)
}