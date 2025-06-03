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
import com.memoittech.cuviewtv.components.VerticalTrackItem
import com.memoittech.cuviewtv.ui.theme.GrayBlue
import com.memoittech.cuviewtv.ui.theme.Violet
import com.memoittech.cuviewtv.viewModel.AppViewModels
import com.memoittech.cuviewtv.viewModel.TracksViewModel
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
fun TracksComponent(navController: NavController , appViewModel: AppViewModels){

    val tracksViewModel : TracksViewModel = viewModel()

    var ordering by remember { mutableStateOf("-created_at") }

    val listState = rememberLazyListState()

    fun onTrackClick(id: Int){
        navController.navigate("track_details/${id}")
    }

    var isFirstLaunch by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        // Scroll-based pagination trigger
        launch {
            snapshotFlow { listState.firstVisibleItemIndex }
                .distinctUntilChanged()
                .collect { index ->
                    if (isFirstLaunch) {
                        isFirstLaunch = false
                        return@collect
                    }

                    if (
                        index >= tracksViewModel.tracks.size - 10 &&
                        !tracksViewModel.isLoading
                    ) {
                        tracksViewModel.getTracksList(ordering, appViewModel.query, 1)
                    }
                }
        }

        // Query change trigger
        launch {
            snapshotFlow { appViewModel.query }
                .debounce(500)
                .distinctUntilChanged()
                .collect { value ->
                    if (value.length >= 3) {
                        tracksViewModel.getTracksList(ordering, value, 0)
                    } else if (value.isEmpty()) {
                        tracksViewModel.getTracksList(ordering, "", 0)
                    }

                    listState.scrollToItem(0)
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
