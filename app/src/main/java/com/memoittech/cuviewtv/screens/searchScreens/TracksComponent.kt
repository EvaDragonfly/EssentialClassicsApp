package com.memoittech.cuviewtv.screens.searchScreens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.memoittech.cuviewtv.components.VerticalTrackItem
import com.memoittech.cuviewtv.viewModel.TracksViewModel
import kotlinx.coroutines.flow.debounce

@Composable
fun TracksComponent(navController: NavController , q : String){

    val tracksViewModel : TracksViewModel = viewModel()

    var ordering by remember { mutableStateOf("-created_at") }

    val listState = rememberLazyListState()

    fun onTrackClick(id: Int){
        navController.navigate("track_details/${id}")
    }

    LaunchedEffect(q) {
        snapshotFlow{ q }
            .debounce(2000) // Wait for 2 seconds of inactivity
            .collect { value ->
                if (value.length >= 3) {
                    tracksViewModel.getTracksList( ordering, q, 0)
                }
            }
        listState.scrollToItem(0)
    }

    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemIndex }
            .collect { index ->
                if (index >= tracksViewModel.tracks.size - 10 && !tracksViewModel.isLoading) {
                    tracksViewModel.getTracksList( ordering, q, 1)
                }
            }
    }


    Column() {
        tracksViewModel.tracks?.let {
            LazyColumn (
                state = listState
            ){
                items(items = it){item ->
                    VerticalTrackItem(
                        item,
                        0,
                        { onTrackClick(item.id) },
                        {
                            navController.navigate(
                                "track_details/${item.id}"
                            )
                        }
                    )
                }
            }
        }
    }

}
