package com.memoittech.cuviewtv.screens.detailScreens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowCircleLeft
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.memoittech.cuviewtv.R
import com.memoittech.cuviewtv.components.HorizontalVideoItem
import com.memoittech.cuviewtv.viewModel.TracksViewModel

@Composable
fun TrackDetailsScreen(navController : NavController, id : Int){

    val trackViewModel : TracksViewModel = viewModel()

    val trackDetails = trackViewModel.trackdetailResponse

    val trackVideos = trackViewModel.trackVideosResponse

    LaunchedEffect(key1 = id) {
        trackViewModel.getTrackDetails(id)
        trackViewModel.getTrackVideos(id)
    }

    fun onFavouriteClick(){
        trackViewModel.addFavoriteTrack(id, false)
    }

    fun onVideoClick(id : Int){
        navController.navigate("player/${id}")
    }

    Surface(
        modifier = Modifier.fillMaxSize()
            .background(Color.LightGray)
            .padding(28.dp)){
        trackDetails?.let {
            Column (
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ) {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowCircleLeft,
                            contentDescription = "Back Button"
                        )
                    }
                }
                Image(
                    painter = painterResource(id = R.drawable.some_image) ,
                    contentDescription = "member image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxWidth()
                        .padding(15.dp)
                )

                Text(
                    text = trackDetails.title,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Normal,
                    maxLines = 2
                )

                Text(
                    text = (trackDetails.performers + trackDetails.composers)
                        .map { it.member_title}
                        .joinToString(","),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Light,
                    color = Color.Green,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )

                Row {
                    IconButton(onClick = { onFavouriteClick() }) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = "Clear search"
                        )
                    }
                    IconButton(onClick = { onFavouriteClick() }) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Clear search"
                        )
                    }
                }


                Text(
                    text = "Videos",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )

                LazyRow {
                    if (trackVideos != null) {
                        items(items = trackVideos.results){it->
                            HorizontalVideoItem(video = it, {onVideoClick(it.id)})
                        }
                    }
                }

            }
        }

    }

}