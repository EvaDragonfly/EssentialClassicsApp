package com.memoittech.cuviewtv.screens.MoodScreens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.memoittech.cuviewtv.R
import com.memoittech.cuviewtv.components.PlayerComponent
import com.memoittech.cuviewtv.components.VerticalTrackItem
import com.memoittech.cuviewtv.model.MoodTrack
import com.memoittech.cuviewtv.ui.theme.DarkBg2

import com.memoittech.cuviewtv.viewModel.MoodsViewModel
import com.memoittech.cuviewtv.viewModel.TracksViewModel
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer


@Composable
fun MoodDetailsScreen(id : Int, navController: NavController){

    val youTubePlayerInstance = remember { mutableStateOf<YouTubePlayer?>(null) }

    var track by remember { mutableStateOf<MoodTrack?>(null) }

    val viewModel : MoodsViewModel = viewModel()

    val trackViewModel : TracksViewModel = viewModel()

    LaunchedEffect(id) {
        viewModel.getMoodTracks(id)
    }

    val tracks = viewModel.moodTracksResponse?.data

    LaunchedEffect(tracks) {
        if (tracks != null) {
            track = tracks.get(0)
            track?.starts_at?.toFloat()
                ?.let { youTubePlayerInstance.value?.loadVideo(track?.video_id.toString(), it) }
        }
    }


    fun onTrackClick(item: MoodTrack){
        if(item.video_id == track?.video_id){
            youTubePlayerInstance.value?.seekTo(item.starts_at.toFloat())
        } else {
            youTubePlayerInstance.value?.loadVideo(item?.video_id.toString(), item.starts_at.toFloat())
        }
        track = item
    }

    fun onFavoriteClick(item: MoodTrack){
        if(item.track.is_favorite){
            trackViewModel.addFavoriteTrack(item.track.id, true)
        } else {
            trackViewModel.addFavoriteTrack(item.track.id, false)
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize()
            .background(DarkBg2)
    ){
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(DarkBg2)
                    .padding(WindowInsets.systemBars.asPaddingValues()),
            ) {
                Column (
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Transparent)
                ){
                    Row( modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp, 15.dp)
                        .align(Alignment.CenterHorizontally)
                        .background(DarkBg2),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ){
                        Image(
                            modifier = Modifier.clickable { navController.popBackStack() },
                            painter = painterResource(R.drawable.backarrow),
                            contentDescription = "back"
                        )
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ){
                            Image(
                                modifier = Modifier.clickable {
//                                    onFavouriteClick()
                                                              },
                                painter = painterResource(R.drawable.favoritewhite),
                                contentDescription = "add favorite"
                            )
                            Image(
                                painter = painterResource(R.drawable.shareicon),
                                contentDescription = "share"
                            )
                            Image(
                                painter = painterResource(R.drawable.dotswhite),
                                contentDescription = "menu"
                            )
                        }
                    }

                    track?.video_id?.let {
                        track?.starts_at?.toFloat()?.let { it1 ->
                            PlayerComponent(it, it1){ player ->
                                youTubePlayerInstance.value = player
                            }
                        }
                    }

//                    videoDetails?.let {
//                    Column(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(20.dp, 15.dp)
//                        ,
//                        verticalArrangement = Arrangement.Center
//                    ) {
//                            Text(
//                                text = it.title,
//                                modifier = Modifier.fillMaxWidth(),
//                                fontSize = 16.sp,
//                                color = Color.White,
//                                textAlign = TextAlign.Center,
//                                fontWeight = FontWeight.W600,
//                                maxLines = 2
//                            )
//
//
//                        Text(
//                            text = videoDetails.title,
//                            modifier = Modifier.fillMaxWidth(),
//                            fontSize = 15.sp,
//                            color = Color.White,
//                            textAlign = TextAlign.Center,
//                            fontWeight = FontWeight.W400,
//                            maxLines = 2,
//                            fontStyle = FontStyle.Italic
//                        )
//
//                    }
//                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .padding(bottom = 10.dp)
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        Color(0x8054609C), // 50% opacity
                                        Color(0x0054609C)
                                    )
                                )
                            )
                    )

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
                                        },
                                        {
                                            onFavoriteClick(item)
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
