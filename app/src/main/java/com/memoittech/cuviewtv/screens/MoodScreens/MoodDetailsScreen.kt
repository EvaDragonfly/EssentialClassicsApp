package com.memoittech.cuviewtv.screens.MoodScreens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.memoittech.cuviewtv.R
import com.memoittech.cuviewtv.components.PlayerComponent
import com.memoittech.cuviewtv.components.Separator
import com.memoittech.cuviewtv.components.VerticalTrackItem
import com.memoittech.cuviewtv.model.MoodTrack
import com.memoittech.cuviewtv.ui.theme.DarkBg2
import com.memoittech.cuviewtv.ui.theme.GrayBlue

import com.memoittech.cuviewtv.viewModel.MoodsViewModel
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer


@Composable
fun MoodDetailsScreen(
    id : Int,
    title : String,
    navController: NavHostController
){

    val youTubePlayerInstance = remember { mutableStateOf<YouTubePlayer?>(null) }

    var track by remember { mutableStateOf<MoodTrack?>(null) }

    val viewModel : MoodsViewModel = viewModel()

    LaunchedEffect(id) {
        viewModel.getMoodTracks(id)
    }

    val tracks = viewModel.moodTracksResponse?.data

    LaunchedEffect(tracks) {
        if (tracks != null) {
            track = tracks[0]
            track?.starts_at?.toFloat()
                ?.let { youTubePlayerInstance.value?.loadVideo(track?.video?.youtube_id.toString(), it) }
        }
    }


    fun onTrackClick(item: MoodTrack){
        if(item.video.youtube_id == track?.video?.youtube_id){
            youTubePlayerInstance.value?.seekTo(item.starts_at.toFloat())
        } else {
            youTubePlayerInstance.value?.loadVideo(item.video.youtube_id, item.starts_at.toFloat())
        }
        track = item
    }

    Surface(
        modifier = Modifier.fillMaxSize()
            .background(DarkBg2)
    ){
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(DarkBg2)
                    .padding(WindowInsets.systemBars.asPaddingValues())
            ) {
                Column (
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Transparent)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp, 15.dp)
                            .align(Alignment.CenterHorizontally)
                            .background(DarkBg2),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Image(
                            modifier = Modifier.clickable {
                                navController.popBackStack()
                                                          },
                            painter = painterResource(R.drawable.backarrow),
                            contentDescription = "back"
                        )

                        Text(
                            text = title,
                            color = GrayBlue,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.W500,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.weight(1f)
                        )
                    }

                    track?.video?.youtube_id?.let {
                        track?.starts_at?.toFloat()?.let { it1 ->
                            PlayerComponent(it, it1) { player ->
                                youTubePlayerInstance.value = player
                            }
                        }
                    }

                    track?.video?.title?.let {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp, 15.dp),
                            verticalArrangement = Arrangement.Center
                        ) {

                            Text(
                                text = it,
                                modifier = Modifier.fillMaxWidth(),
                                fontSize = 16.sp,
                                color = Color.White,
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.W600,
                                maxLines = 2
                            )
                        }
                    }

                    Separator()

                    LazyColumn(modifier = Modifier
                        .padding(10.dp, 0.dp)
                        .background(Color.Transparent)) {
                        tracks?.let {
                            items(items = it) { item ->
                                track?.track?.let { it1 ->
                                    VerticalTrackItem(
                                        track = item.track,
                                        active_track_id = it1.id,
                                        {
                                            onTrackClick(item)
                                        },
                                        {
                                            navController.navigate(
                                                "track_details/${item.track.id}"
                                            )
                                        }
                                    )
                                }
                            }
                            item {
                                if (viewModel.isLoading){
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
        }
    }
