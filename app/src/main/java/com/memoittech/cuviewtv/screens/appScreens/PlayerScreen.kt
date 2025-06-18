    package com.memoittech.cuviewtv.screens.appScreens

    import android.annotation.SuppressLint
    import android.util.Log
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
    import androidx.compose.foundation.lazy.rememberLazyListState
    import androidx.compose.material3.Surface
    import androidx.compose.material3.Text
    import androidx.compose.runtime.Composable
    import androidx.compose.runtime.LaunchedEffect
    import androidx.compose.runtime.getValue
    import androidx.compose.runtime.mutableStateListOf
    import androidx.compose.runtime.mutableStateOf
    import androidx.compose.runtime.remember
    import androidx.compose.runtime.rememberCoroutineScope
    import androidx.compose.runtime.setValue
    import androidx.compose.runtime.snapshotFlow
    import androidx.compose.ui.Alignment
    import androidx.compose.ui.Modifier
    import androidx.compose.ui.graphics.Color
    import androidx.compose.ui.platform.LocalContext
    import androidx.compose.ui.res.painterResource
    import androidx.compose.ui.text.font.FontStyle
    import androidx.compose.ui.text.font.FontWeight
    import androidx.compose.ui.text.style.TextAlign
    import androidx.compose.ui.unit.dp
    import androidx.compose.ui.unit.sp
    import androidx.lifecycle.viewmodel.compose.viewModel
    import androidx.navigation.NavHostController
    import com.memoittech.cuviewtv.R
    import com.memoittech.cuviewtv.components.PlayerComponent
    import com.memoittech.cuviewtv.components.Separator
    import com.memoittech.cuviewtv.components.VideoTrackItem
    import com.memoittech.cuviewtv.components.formatSecondsToTime
    import com.memoittech.cuviewtv.components.shareLink
    import com.memoittech.cuviewtv.model.Track
    import com.memoittech.cuviewtv.model.VideoTrack
    import com.memoittech.cuviewtv.ui.theme.DarkBg2
    import com.memoittech.cuviewtv.ui.theme.GrayBlueLight
    import com.memoittech.cuviewtv.viewModel.VideosViewModel
    import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
    import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.YouTubePlayerTracker
    import kotlinx.coroutines.delay
    import kotlinx.coroutines.launch

    @SuppressLint("UnrememberedMutableState")
    @Composable
    fun PlayerScreen(
        navController: NavHostController,
        id: Int,
        starts_at : Int
    ) {

        val videoViewModel : VideosViewModel = viewModel()

        val videoDetails = videoViewModel.videodetailResponse

        val listState = rememberLazyListState()

        val scope = rememberCoroutineScope()

        val context = LocalContext.current

        val youTubePlayerInstance = remember { mutableStateOf<YouTubePlayer?>(null) }

        val youTubePlayerTracker = remember { YouTubePlayerTracker() }

        val currentTrack = remember { mutableStateOf<VideoTrack?>(null) }

        var video by remember { mutableStateOf(videoDetails) }

        var tracks by remember { mutableStateOf<List<VideoTrack>>(emptyList()) }

        LaunchedEffect(key1 = id) {
            videoViewModel.getVideoDetails(id)
        }

        LaunchedEffect(videoDetails) {
            video = videoDetails
            video?.tracks?.let {
                tracks = it
            }
            if (tracks?.size!! > 0){
                currentTrack.value = tracks.find { track ->
                    track.starts_at == starts_at
                }

                currentTrack.value?.starts_at?.let { youTubePlayerInstance.value?.seekTo(it.toFloat()) }
                val index = tracks.indexOfFirst { it.starts_at == starts_at }.takeIf { it >= 0 } ?: 0
                scope.launch {
                    listState.animateScrollToItem(index = index) // Scroll to item #20
                }
            }
        }


        LaunchedEffect(youTubePlayerTracker) {
            while (true) {
                val currentTime = youTubePlayerTracker.currentSecond.toInt()

                if (currentTrack.value != null && tracks.size > 0){

                    val matchingTrack = tracks.find { track ->
                        currentTime in track.starts_at until track.ends_at
                    }


                    if (matchingTrack != null && matchingTrack != currentTrack.value && currentTime > 0) {
                        currentTrack.value = matchingTrack
                        // Optionally trigger something when track changes
                        // e.g., update UI, send analytics, etc.

                        val index = tracks.indexOfFirst { it.position == currentTrack.value!!.position }.takeIf { it >= 0 } ?: 0
                        scope.launch {
                            listState.animateScrollToItem(index = index) // Scroll to item #20
                        }
                    }

                }

                delay(500) // Poll every 500ms
            }
        }


        fun onFavouriteClick() {
            if (video?.is_favorite == true) {
                videoViewModel.addFavoriteVideo(id, true)
            } else {
                videoViewModel.addFavoriteVideo(id, false)
            }
            video = video?.copy(is_favorite = !video?.is_favorite!!)
        }

        Surface(
            modifier = Modifier.fillMaxSize()
                .background(DarkBg2)
                .padding(WindowInsets.systemBars.asPaddingValues())
        ){
            video?.let {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(DarkBg2)
                ) {
                    Column (
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Transparent)
                    ){
                        Row( modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp)
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
                                    modifier = Modifier.clickable { onFavouriteClick() },
                                    painter = painterResource(
                                        if(!video!!.is_favorite)
                                        R.drawable.favoritewhite
                                        else R.drawable.favoritewhitefill
                                    ),
                                    contentDescription = "add favorite"
                                )
                                Image(
                                    painter = painterResource(R.drawable.shareicon),
                                    contentDescription = "share",
                                    modifier = Modifier.clickable {
                                        shareLink(context, "https://www.youtube.com/watch?v=${video!!.youtube_id}")
                                    }
                                )
                            }
                        }

                        PlayerComponent(
                            video!!.youtube_id,
                            starts_at.toFloat(),
                            youTubePlayerTracker,
                        ){ player ->
                            youTubePlayerInstance.value = player
                            player.addListener(youTubePlayerTracker)
                        }

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp, 15.dp)
                            ,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = video!!.title,
                                modifier = Modifier.fillMaxWidth(),
                                fontSize = 16.sp,
                                color = Color.White,
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.W600,
                                maxLines = 2
                            )

                            Text(
                                text = video!!.title,
                                modifier = Modifier.fillMaxWidth(),
                                fontSize = 15.sp,
                                color = Color.White,
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.W400,
                                maxLines = 2,
                                fontStyle = FontStyle.Italic
                            )

                            Text(
                                text = "",
//                                text = videoTracks?.size.toString() + ". " + videoDetails?.let {
//                                    formatSecondsToTime(
//                                        it.duration)
//                                },
                                modifier = Modifier.fillMaxWidth(),
                                fontSize = 13.sp,
                                color = Color.White,
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.W400,
                                maxLines = 2,
                            )
                        }

                        Separator()

                        LazyColumn(
                            state = listState,
                            modifier = Modifier
                            .background(Color.Transparent)) {
                            item {
                                Text(
                                    text = video!!.description,
                                    modifier = Modifier.fillMaxWidth()
                                        .padding(bottom = 20.dp),
                                    fontSize = 13.sp,
                                    color = GrayBlueLight,
                                    textAlign = TextAlign.Center,
                                    fontWeight = FontWeight.W400
                                )
                            }

                            items(items = tracks){
                                currentTrack.value?.starts_at?.let { it1 ->
                                    VideoTrackItem(
                                        track = it,
                                        selectedTrack = it1,
                                        {
                                            youTubePlayerInstance.value?.seekTo(it.starts_at.toFloat())
                                            youTubePlayerInstance.value?.play()
                                        },
                                        {
                                            navController.navigate(
                                                "track_details/${it.track.id}"
                                            )
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
