package com.marparedes.citieslistapp.presentation.screens.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.dp
import com.marparedes.citieslistapp.domain.model.City

@Composable
fun CitiesList(
    cities: List<City>,
    isLoading: Boolean,
    hasMoreData: Boolean,
    loadNextPage: () -> Unit,
    onItemClick: (City) -> Unit,
    modifier: Modifier = Modifier
//    listState: LazyListState
) {
    val listState = rememberLazyListState()

    LazyColumn(
        state = listState,
        modifier = modifier.fillMaxSize()
    ) {
        itemsIndexed(
            items = cities,
            key = { _, item -> item.id }
        ){ index, item ->
            CitiesListItem(
                item = item,
                onItemClick = { selectedCity ->
                    onItemClick(selectedCity)
                },
                isGray = index % 2 == 1
            )
        }

        if (isLoading && hasMoreData) {
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

    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo }
            .collect { layoutInfo ->
                val visibleItems = layoutInfo.visibleItemsInfo
                val totalItems = layoutInfo.totalItemsCount
                if (visibleItems.isNotEmpty() && totalItems > 0) {
                    val lastVisibleItemIndex = visibleItems.last().index
                    if (lastVisibleItemIndex == totalItems - 1 && hasMoreData) {
                        loadNextPage()
                    }
                }
            }
    }
}