package com.memoittech.cuviewtv.screens.searchScreens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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

    var limit by remember { mutableStateOf(0) }
    var offset by remember { mutableStateOf(0) }
    var ordering by remember { mutableStateOf("position") }

    tracksViewModel.getTracksList(limit, offset, ordering, q)

    fun onTrackClick(id: Int){
        navController.navigate("track_details/${id}")
    }

    LaunchedEffect(q) {
        snapshotFlow{ q }
            .debounce(2000) // Wait for 2 seconds of inactivity
            .collect { value ->
                if (value.length >= 3) {
                    tracksViewModel.getTracksList(limit, offset, ordering, q)
                }
            }
    }

    Column() {
        tracksViewModel.tracksResponse?.let {
            LazyColumn (){
                items(items = it.results){item ->
                    VerticalTrackItem(
                        item,
                        0,
                        { onTrackClick(item.id) },
                        {
                            navController.navigate(
                                "track_details/${item.id}"
                            )
                        },
                        {
                            tracksViewModel.addFavoriteTrack(item.id, false)
                        }
                    )
                }
            }
        }
    }

}
