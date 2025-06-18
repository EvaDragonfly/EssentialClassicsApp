package com.memoittech.cuviewtv.screens.SliderScreens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.memoittech.cuviewtv.R
import com.memoittech.cuviewtv.components.VideoOvalItem
import com.memoittech.cuviewtv.ui.theme.GrayBlue
import com.memoittech.cuviewtv.viewModel.AppViewModels
import com.memoittech.cuviewtv.viewModel.VideosViewModel


@Composable
fun SliderVideosComponent(navController: NavHostController, appViewModel: AppViewModels) {

    val viewModels : VideosViewModel = viewModel()

    LaunchedEffect(Unit) {
        viewModels.getVideosList( "-created_at", "", 0)
    }

    val videos = viewModels.videos

    Column (modifier = Modifier.fillMaxWidth()){
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp, 5.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = "Latest Videos",
                fontSize = 18.sp,
                fontWeight = FontWeight.W600,
                color = Color.White
            )
            Row(
                modifier = Modifier
                    .clickable {
                        appViewModel.onIndexChanged(3)
                        navController.navigate("main/search")
                               },
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    modifier = Modifier
                        .padding(10.dp, 0.dp),
                    text = "Show All",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.W300,
                    color = GrayBlue
                )
                Image(
                    painter = painterResource(R.drawable.rightarrow),
                    contentDescription = "Show All"
                )
            }
        }
        videos?.let {
            LazyRow (
                modifier = Modifier.padding(0.dp, 15.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ){
                items(items = it){item ->
                    VideoOvalItem(id = item.id, youtube_id = item.youtube_id, title = item.title, has_thumbnail = item.has_thumbnail) {
                        navController.navigate("player/${item.id}/0")
                    }
                }
            }

        }
    }
}