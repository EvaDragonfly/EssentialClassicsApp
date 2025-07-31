package com.memoittech.cuviewtv.screens.searchScreens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.memoittech.cuviewtv.components.VerticalTrackItem
import com.memoittech.cuviewtv.ui.theme.GrayBlue
import com.memoittech.cuviewtv.ui.theme.Violet
import com.memoittech.cuviewtv.viewModel.AppViewModels
import com.memoittech.cuviewtv.viewModel.TracksViewModel
import com.memoittech.cuviewtv.viewModel.VideosViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.launch

@SuppressLint("FlowOperatorInvokedInComposition")
@OptIn(FlowPreview::class)
@Composable
fun TracksComponent(navController: NavHostController , appViewModel: AppViewModels){

    var ordering by remember { mutableStateOf("-created_at") }

    val tracksViewModel : TracksViewModel = viewModel()

    val listState = rememberLazyListState()

    var isFirstLaunch by remember { mutableStateOf(true) }

    var page by remember { mutableStateOf(0) }

    fun onTrackClick(id: Int){
        navController.navigate("track_details/${id}")
    }

    LaunchedEffect(appViewModel.query) {
        page = 0
        if (appViewModel.query.length >= 2) {
            tracksViewModel.getTracksList( ordering, appViewModel.query, 0)
            isFirstLaunch = false
        } else if (appViewModel.query.isEmpty()) {
            if(tracksViewModel.tracks.size == 0 || !isFirstLaunch){
                tracksViewModel.getTracksList( ordering, "", 0)
                isFirstLaunch = false
            }
        }
    }

    LaunchedEffect(page) {
        if (page > 0){
            tracksViewModel.getTracksList( ordering, appViewModel.query, 1)
            isFirstLaunch = false
        }
    }

    LaunchedEffect(Unit) {

        // Scroll-based pagination trigger
        launch {
            snapshotFlow { listState.firstVisibleItemIndex }
                .distinctUntilChanged()
                .collect { index ->
                    if (
                        index >= tracksViewModel.tracks.size - 10 &&
                        !tracksViewModel.isLoading
                    ) {
                        page += 1
                    }
                }
        }

    }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        tracksViewModel.tracks.let {
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
                item {
                    if (tracksViewModel.isLoading){
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "Loading...",
                            textAlign = TextAlign.Center,
                            color = GrayBlue
                        )
                    }
                }
            }
        }
    }

}
