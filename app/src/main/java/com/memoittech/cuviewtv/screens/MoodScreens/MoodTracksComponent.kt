package com.memoittech.cuviewtv.screens.MoodScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.memoittech.cuviewtv.components.VerticalTrackItem
import com.memoittech.cuviewtv.model.MoodTrack
import com.memoittech.cuviewtv.ui.theme.GrayBlueLight
import com.memoittech.cuviewtv.viewModel.MoodsViewModel
import com.memoittech.cuviewtv.viewModel.TracksViewModel


@Composable
fun MoodTracksComponent(
    navController: NavController,
    id : Int,
    description : String,
    getVideoId: (Int) -> Unit,
    onTrackClick : (MoodTrack) -> Unit
) {

    val viewModel : MoodsViewModel = viewModel()

    val trackViewModel : TracksViewModel = viewModel()

    LaunchedEffect(Unit) {
        viewModel.getMoodTracks(id)
    }

    val tracks = viewModel.moodTracksResponse?.data

    LaunchedEffect(tracks) {
        tracks?.first()?.let {
            getVideoId(it.video_id)
        }
    }

    LazyColumn(modifier = Modifier
    .padding(10.dp, 0.dp)
    .background(Color.Transparent)) {
        description?.let {
            item {
                Text(
                    text = description,
                    modifier = Modifier.fillMaxWidth(),
                    fontSize = 13.sp,
                    color = GrayBlueLight,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.W400
                )
            }
        }
        tracks?.let {
            items(items = it) { item ->
                VerticalTrackItem(
                    track = item.track,
                    {
                        onTrackClick(item)
                    },
                    {
                        navController.navigate(
                            "track_details/${item.track.id}"
                        )
                    },
                    {
                        trackViewModel.addFavoriteTrack(item.track.id, false)
                    }
                )
            }
        }
    }
}