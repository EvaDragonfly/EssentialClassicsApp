package com.memoittech.cuviewtv.screens.favoritesScreens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.memoittech.cuviewtv.R
import com.memoittech.cuviewtv.components.Separator
import com.memoittech.cuviewtv.components.VideoOvalItem
import com.memoittech.cuviewtv.ui.theme.DarkBg2
import com.memoittech.cuviewtv.viewModel.VideosViewModel


@Composable
fun FavoriteVideosScreen(navController : NavController) {

    val videosViewModel : VideosViewModel = viewModel()

    var ordering by remember { mutableStateOf("-created_at") }

    val listState = rememberLazyListState()

    LaunchedEffect(Unit) {
        videosViewModel.getFavoriteVideos(ordering)
    }

    val favoriteVideos = videosViewModel.favouriteVideos

    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemIndex }
            .collect { index ->
                if (index >= videosViewModel.videos.size/2 - 10  && !videosViewModel.isFavoriteVideosLoading) {
                    videosViewModel.getFavoriteVideos(ordering)
                }
            }
    }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBg2)
            .padding(
                WindowInsets.systemBars.asPaddingValues()
            )
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            Image(
                modifier = Modifier.clickable {
                    navController.popBackStack()
                },
                painter = painterResource(R.drawable.backarrow),
                contentDescription = "go back"
            )
        }

        Text(
            text = "Videos",
            modifier = Modifier
                .padding(20.dp, 10.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Start,
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.W600
        )

        Separator()

        favoriteVideos.let {
            LazyColumn (
                state = listState,
                modifier = Modifier
                    .background(DarkBg2)
                    .padding(0.dp, 10.dp),
                verticalArrangement = Arrangement.Center){
                items(items = it.chunked(2)) { rowItem ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp, 5.dp),
                        horizontalArrangement = Arrangement.SpaceAround
                    )
                    {
                        for (it in rowItem) {
                            VideoOvalItem(id = it.video.id, youtube_id = it.video.youtube_id, title = it.video.title, has_thumbnail = it.video.has_thumbnail) {
                                navController.navigate("player/${it.video.id}/${0f}")
                            }
                        }
                        if (rowItem.size < 2) {
                            Spacer(modifier = Modifier.weight(1f)) // Fill empty space if only 1 item in the last row
                        }
                    }
                }
            }
        }

    }
}