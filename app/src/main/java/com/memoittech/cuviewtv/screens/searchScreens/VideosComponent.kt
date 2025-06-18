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
import com.memoittech.cuviewtv.components.VideoOvalItem
import com.memoittech.cuviewtv.ui.theme.DarkBg2
import com.memoittech.cuviewtv.ui.theme.GrayBlue
import com.memoittech.cuviewtv.ui.theme.Violet
import com.memoittech.cuviewtv.viewModel.AppViewModels
import com.memoittech.cuviewtv.viewModel.VideosViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
@Composable
fun VideosComponent(navController: NavHostController , appViewModel: AppViewModels){

    val videosViewModel : VideosViewModel = viewModel()

    var ordering by remember { mutableStateOf("-created_at") }

    val listState = rememberLazyListState()

    var isFirstLaunch by remember { mutableStateOf(true) }


    LaunchedEffect(Unit) {
        // Scroll-based pagination trigger
        launch {
            snapshotFlow { listState.firstVisibleItemIndex }
                .distinctUntilChanged()
                .collect { index ->
                    if (
                        index >= videosViewModel.videos.size/2 - 10 &&
                        !videosViewModel.isLoading
                    ) {
                        videosViewModel.getVideosList(ordering, appViewModel.query, 1)
                    }
                }
        }

        // Query change trigger
        launch {
            snapshotFlow { appViewModel.query }
                .debounce(500)
                .distinctUntilChanged()
                .collect { value ->
                    if (isFirstLaunch) {
                        isFirstLaunch = false
                        return@collect
                    }

                    if (value.length >= 3) {
                        videosViewModel.getVideosList(ordering, value, 0)
                    } else if (value.isEmpty()) {
                        videosViewModel.getVideosList(ordering, "", 0)
                    }

                    listState.scrollToItem(0)
                }
        }
    }



    Column(modifier = Modifier.fillMaxWidth()) {
        videosViewModel.videos.let {
            LazyColumn (
                state = listState,
                modifier = Modifier
                    .background(DarkBg2)
                    .padding(0.dp, 10.dp),
                verticalArrangement = Arrangement.Center
            ){
                items(items = it.chunked(2)) { rowItem ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp, 5.dp),
                        horizontalArrangement = Arrangement.SpaceAround
                    )
                    { for (it in rowItem) {
                        VideoOvalItem(id = it.id, youtube_id = it.youtube_id, title = it.title, has_thumbnail = it.has_thumbnail) {
                            navController.navigate("player/${it.id}/0")
                        }
                    }
                        if (rowItem.size < 2) {
                            Spacer(modifier = Modifier.weight(1f)) // Fill empty space if only 1 item in the last row
                        }
                    }
                }
                item {
                    if (videosViewModel.isLoading){
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



