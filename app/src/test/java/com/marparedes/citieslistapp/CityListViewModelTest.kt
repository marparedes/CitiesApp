package com.marparedes.citieslistapp

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.marparedes.citieslistapp.common.Resource
import com.marparedes.citieslistapp.data.model.Coordinates
import com.marparedes.citieslistapp.domain.model.City
import com.marparedes.citieslistapp.domain.usecase.cities.GetCitiesUseCase
import com.marparedes.citieslistapp.presentation.viewmodel.CityListViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class CityListViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @MockK
    private lateinit var getCitiesUseCase : GetCitiesUseCase

    private lateinit var viewModel: CityListViewModel
    private val dispatcher: TestDispatcher = UnconfinedTestDispatcher()

    private val allCities = listOf(
        City(id = "707860", name = "Hurzuf", country="UA", coord = Coordinates(52.3676, 4.9041)),
        City(id = "1283378", name = "Gorkhā", country="NP", coord = Coordinates(52.52, 13.4050)),
        City(id = "708546", name = "Holubynka", country="UA", coord = Coordinates(48.8566, 2.3522)))

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        coEvery { getCitiesUseCase.invoke() } returns flow {
            emit(Resource.Loading)
            emit(Resource.Success(allCities))
        }
        viewModel = CityListViewModel(getCitiesUseCase)
    }

    @Test
    fun `filterCities with matching query returns filtered cities`(): Unit = runTest {

        viewModel.filterCities("h")

        advanceUntilIdle()

        val state = viewModel.state.value
        assert(state.cities.size == 2)
        assertEquals(state.cities[0].name, "Holubynka")
        assertEquals(state.cities[1].name, "Hurzuf")
    }

    @Test
    fun `filterCities with empty query returns all cities`() = runTest {

        viewModel.filterCities("")

        val state = viewModel.state.value
        assertEquals(3, state.cities.size)
        assertTrue(state.cities.any { it.name == "Hurzuf" })
        assertTrue(state.cities.any { it.name == "Gorkhā" })
        assertTrue(state.cities.any { it.name == "Holubynka" })
    }

    @Test
    fun `filterCities with no matching query returns empty list`() = runTest {
        viewModel.filterCities("London")
        val state = viewModel.state.value
        assertEquals(0, state.cities.size)
    }

}