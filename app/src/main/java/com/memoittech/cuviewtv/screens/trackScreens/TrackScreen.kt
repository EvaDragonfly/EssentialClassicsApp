package com.memoittech.cuviewtv.screens.trackScreens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.memoittech.cuviewtv.R
import com.memoittech.cuviewtv.components.VideoOvalItem
import com.memoittech.cuviewtv.components.formatSecondsToTime
import com.memoittech.cuviewtv.ui.theme.DarkBg2
import com.memoittech.cuviewtv.ui.theme.Violet
import com.memoittech.cuviewtv.ui.theme.Yellow
import com.memoittech.cuviewtv.viewModel.TracksViewModel


@Composable
fun TrackScreen(id : Int, navController: NavController){

    val tracksViewModel : TracksViewModel = viewModel()

    LaunchedEffect(Unit) {
        tracksViewModel.getTrackDetails(id)
        tracksViewModel.getTrackVideos(id)
    }

    val trackDetails = tracksViewModel.trackdetailResponse

    val trackVideos = tracksViewModel.trackVideos

    var track by remember { mutableStateOf(trackDetails) }

    LaunchedEffect(trackDetails) {
        track = trackDetails
    }

    fun onFavoriteClick(id : Int){
        if(track?.is_favorite == true){
            tracksViewModel.addFavoriteTrack(id, true)
        } else {
            tracksViewModel.addFavoriteTrack(id, false)
        }
        track = track?.copy(is_favorite = !track?.is_favorite!!)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBg2)
    ) {
        track?.let{
            Column (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(WindowInsets.systemBars.asPaddingValues())
            ){
                Row( modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp, 15.dp)
                    .align(Alignment.CenterHorizontally)
                    .background(DarkBg2),
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Image(
                        modifier = Modifier.clickable {
                            navController.popBackStack()
                        },
                        painter = painterResource(R.drawable.backarrow),
                        contentDescription = "back"
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ){
                        Image(
                            modifier = Modifier.clickable {
                                onFavoriteClick(track!!.id)
                            },
                            painter = painterResource(
                                if(!track!!.is_favorite)
                                    R.drawable.favoritewhite
                                else R.drawable.favoritewhitefill
                            ),
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
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp, 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Image(
                        painter = painterResource(R.drawable.notesicon),
                        contentDescription = "Note"
                    )
                    Column(modifier = Modifier.padding(15.dp, 0.dp)) {
                        Text(
                            text = track!!.title,
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.W700
                        )
                        Row(modifier = Modifier.padding(0.dp, 5.dp)){
                            Text(
                                modifier = Modifier.padding(end = 10.dp),
                                text = "Duration",
                                color = Yellow,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.W500
                            )

                            Text(
                                text = formatSecondsToTime(track!!.duration),
                                color = Color.White,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.W500
                            )
                        }
                    }

                }

                Row(
                    modifier = Modifier.padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    AsyncImage(
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape)
                            .border(1.dp, Yellow, CircleShape),
                        model = ImageRequest.Builder(LocalContext.current)
                            .data("https://img.cugate.com/?o=member&i=${4754}&s=300")
                            .crossfade(true)
                            .build(),
                        placeholder = painterResource(id=R.drawable.some_image),
                        error = painterResource(id=R.drawable.some_image),
                        contentDescription = "Video thumbnail",
                        contentScale = ContentScale.Crop
                    )

                    Column(
                        modifier = Modifier.padding(start = 10.dp)
                    ) {
                        Text(
                            modifier = Modifier.padding(end = 10.dp),
                            text = "Composer",
                            color = Yellow,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.W500
                        )
                        Text(
                            text = track!!.composers
                                .map { it.member_title}
                                .joinToString(","),
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.W500,
                            maxLines = 2
                        )
                    }
                }

                Divider(
                    color = Violet,
                    thickness = 1.dp,
                )

                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center
                ) {
                    item {
                        Row(
                            modifier = Modifier.padding(20.dp, 10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ){
                            Image(
                                painter = painterResource(R.drawable.membericon),
                                contentDescription = "Member Icon"
                            )
                            Text(
                                modifier = Modifier.padding(start = 10.dp),
                                text = track!!.performers
                                    .map { it.member_title}
                                    .joinToString(","),
                                color = Color.White,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.W700,
                                maxLines = 2
                            )
                        }
                    }
                    item {
                        AsyncImage(
                            modifier = Modifier
                                .padding(bottom = 20.dp)
                                .height(200.dp)
                                .fillMaxWidth(),
                            model = ImageRequest.Builder(LocalContext.current)
                                .data("https://img.cugate.com/?o=member&i=${4754}&s=300")
                                .crossfade(true)
                                .build(),
                            placeholder = painterResource(id=R.drawable.some_image),
                            error = painterResource(id=R.drawable.some_image),
                            contentDescription = "Video thumbnail",
                            contentScale = ContentScale.Crop
                        )
                    }

                    trackVideos.let {
                        if(trackVideos.size == 1){
                            item {
                                Column(
                                    modifier = Modifier
                                        .padding(top = 30.dp)
                                        .fillMaxWidth(),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ){
                                    Box(
                                        modifier = Modifier
                                            .width(360.dp)
                                            .height(205.dp),
                                        contentAlignment = Alignment.BottomEnd,

                                        ){
                                        AsyncImage(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .clip(RoundedCornerShape(6.dp)),
                                            model = ImageRequest.Builder(LocalContext.current)
                                                .data("https://img.youtube.com/vi/${trackVideos[0]?.video?.id}/maxresdefault.jpg")
                                                .crossfade(true)
                                                .build(),
                                            placeholder = painterResource(id= R.drawable.some_image),
                                            error = painterResource(id= R.drawable.some_image),
                                            contentDescription = "Video thumbnail",
                                            contentScale = ContentScale.Crop
                                        )
                                        Image(
                                            painter = painterResource(R.drawable.playericon),
                                            contentDescription = "Play",
                                            modifier = Modifier.padding(15.dp)
                                                .clickable {
                                                    navController.navigate("player/${trackVideos[0]?.video?.id}")
                                                }
                                        )
                                    }
                                    trackVideos[0]?.video?.title?.let { it1 ->
                                        Text(
                                            text = it1,
                                            color = Color.White,
                                            fontSize = 16.sp,
                                            maxLines = 1,
                                            textAlign = TextAlign.Center,
                                            fontWeight = FontWeight.W600,
                                            modifier = Modifier
                                                .align(alignment = Alignment.CenterHorizontally)
                                                .width(360.dp)
                                                .padding(5.dp, 3.dp),
                                        )
                                    }
                                    trackVideos[0]?.video?.let { it1 ->
                                        Text(
                                            text = it1.description,
                                            color = Color.White,
                                            fontSize = 15.sp,
                                            maxLines = 1,
                                            fontWeight = FontWeight.W400,
                                            fontStyle = FontStyle.Italic,
                                            textAlign = TextAlign.Center,
                                            modifier = Modifier
                                                .align(alignment = Alignment.CenterHorizontally)
                                                .width(360.dp)
                                                .padding(5.dp, 0.dp, 5.dp, 0.dp)
                                        )
                                    }
                                }
                            }
                        } else {
                            items(items = it.chunked(2)){ rowItem ->
                                Row(modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp, 5.dp),
                                    horizontalArrangement = Arrangement.SpaceAround
                                )
                                {
                                    for (it in rowItem) {
                                        it?.let { it1 ->
                                            VideoOvalItem(video = it1.video, {
                                                navController.navigate("player/${it.video.id}")
                                            })
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
        }
    }

}


//@Preview
//@Composable
//fun prevTrack(){
//    TrackScreen()
//}