package com.memoittech.cuviewtv.screens.MoodScreens

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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.memoittech.cuviewtv.R
import com.memoittech.cuviewtv.components.PlayerComponent
import com.memoittech.cuviewtv.model.VideoTrack
import com.memoittech.cuviewtv.ui.theme.DarkBg2
import com.memoittech.cuviewtv.viewModel.VideosViewModel
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer


@Composable
fun MoodDetailsScreen(id : Int, navController: NavController){

    val videoViewModel : VideosViewModel = viewModel()

    val youTubePlayerInstance = remember { mutableStateOf<YouTubePlayer?>(null) }

    var video_id = remember { mutableStateOf<Int?>(null) }

    var selected_track_id = remember { mutableStateOf<Int?>(null) }

    var selected_video_id = remember { mutableStateOf<Int?>(null) }

    LaunchedEffect(video_id.value) {
        video_id.value?.let { videoViewModel.getVideoDetails(it) }
        video_id.value?.let { videoViewModel.getVideoTracks(it) }
    }

    val videoDetails = videoViewModel.videodetailResponse
    val videoTracks = videoViewModel.videoTracksResponse

    LaunchedEffect(selected_track_id.value) {
        if (videoTracks != null) {
            var track = videoTracks.results.find { it.track.id === selected_track_id.value }
            if (track!=null){
                youTubePlayerInstance.value?.seekTo(track.starts_at.toFloat())
            } else {
                selected_video_id.value?.let { videoViewModel.getVideoDetails(it) }
            }
        }
    }

    LaunchedEffect(videoDetails) {
        youTubePlayerInstance.value?.loadVideo(videoDetails?.youtube_id.toString(), 0f)
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

                    videoDetails?.youtube_id?.let {
                        PlayerComponent(it){ player ->
                            youTubePlayerInstance.value = player
                        }
                    }

                    videoDetails?.let {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp, 15.dp)
                        ,
                        verticalArrangement = Arrangement.Center
                    ) {
                            Text(
                                text = it.title,
                                modifier = Modifier.fillMaxWidth(),
                                fontSize = 16.sp,
                                color = Color.White,
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.W600,
                                maxLines = 2
                            )


                        Text(
                            text = videoDetails.title,
                            modifier = Modifier.fillMaxWidth(),
                            fontSize = 15.sp,
                            color = Color.White,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.W400,
                            maxLines = 2,
                            fontStyle = FontStyle.Italic
                        )

                    }
                    }

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

                    MoodTracksComponent(
                        id,
                        videoDetails?.description.toString(),
                        {
                            it -> video_id.value = it
                            println("from func "+ it)
                        },
                        {
                            it ->
                            selected_track_id.value = it.track.id
                            selected_video_id.value = it.video_id
                        }
                    )

                }
            }
        }
    }
