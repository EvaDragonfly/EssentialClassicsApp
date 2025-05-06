package com.memoittech.cuviewtv.screens.searchScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.memoittech.cuviewtv.components.VideoOvalItem
import com.memoittech.cuviewtv.ui.theme.DarkBg2
import com.memoittech.cuviewtv.viewModel.VideosViewModel
import kotlinx.coroutines.flow.debounce

@Composable
fun VideosComponent(navController: NavController , q : String){

    val videosViewModel : VideosViewModel = viewModel()

    var limit by remember { mutableStateOf(0) }
    var offset by remember { mutableStateOf(0) }
    var ordering by remember { mutableStateOf("position") }

    videosViewModel.getVideosList(limit, offset, ordering, q)

    LaunchedEffect(q) {
        snapshotFlow{ q }
            .debounce(2000) // Wait for 2 seconds of inactivity
            .collect { value ->
                if (value.length >= 3) {
                    videosViewModel.getVideosList(limit, offset, ordering, q)
                }
            }
    }


    videosViewModel.videosResponse?.let {
        LazyColumn (
            modifier = Modifier
            .background(DarkBg2)
            .padding(0.dp, 10.dp),
            verticalArrangement = Arrangement.Center){
            items(items = it.results.chunked(2)) { rowItem ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp, 5.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                )
                {
                    for (it in rowItem) {
                        VideoOvalItem(video = it, {
                            navController.navigate("player/${it.id}")
                        })
                    }
                    if (rowItem.size < 2) {
                        Spacer(modifier = Modifier.weight(1f)) // Fill empty space if only 1 item in the last row
                    }
                }
            }
        }
    }
}


