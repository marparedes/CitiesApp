package com.marparedes.citieslistapp.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.marparedes.citieslistapp.presentation.screens.component.CityListItem
import com.marparedes.citieslistapp.presentation.screens.component.FilterInput
import com.marparedes.citieslistapp.presentation.viewmodel.CityListViewModel

@Composable
fun CitiesListScreen(modifier: Modifier = Modifier) {

    val viewModel: CityListViewModel = hiltViewModel()
    val context = LocalContext.current
    val result = viewModel.state.value

    val listState = rememberLazyListState()

    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo }
            .collect { layoutInfo ->
                val visibleItems = layoutInfo.visibleItemsInfo
                val totalItems = layoutInfo.totalItemsCount
                if (visibleItems.isNotEmpty() && totalItems > 0) {
                    val lastVisibleItemIndex = visibleItems.last().index
                    if (lastVisibleItemIndex == totalItems - 1 && viewModel.hasMoreData) {
                        viewModel.loadNextPage()
                    }
                }
            }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .imePadding()
    ) {

        FilterInput(viewModel = viewModel)

        if (result.isLoading && result.cities.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(50.dp)
                )
            }
        } else {
            LazyColumn(
                state = listState,
                modifier = Modifier.fillMaxSize()
            ) {
                itemsIndexed(
                    items = result.cities,
                    key = { _, item -> item.id }
                ){ index, item ->
                    CityListItem(
                        item = item,
                        onItemClick = { city ->
                            Toast.makeText(context, city.name, Toast.LENGTH_SHORT).show()
                        },
                        isGray = index % 2 == 1
                    )
                }

                if (result.isLoading && viewModel.hasMoreData) {
                    item {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                                .wrapContentWidth(Alignment.CenterHorizontally)
                        )
                    }
                }
            }
        }

    }


}

