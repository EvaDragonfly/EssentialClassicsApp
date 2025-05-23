package com.memoittech.cuviewtv.screens.detailScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.memoittech.cuviewtv.components.VideoOvalItem
import com.memoittech.cuviewtv.ui.theme.DarkBg2
import com.memoittech.cuviewtv.viewModel.MembersViewModel

@Composable
fun MemberVideosComponent(navController: NavController, id: Int){

    val viewModel : MembersViewModel = viewModel()

    LaunchedEffect(Unit) {
        viewModel.getMemberVideos(id)
    }

    val memberVideos = viewModel.membersVideoResponse


    LazyColumn(
        modifier = Modifier
            .background(DarkBg2)
            .padding(0.dp, 10.dp),
        verticalArrangement = Arrangement.Center
    ) {
        memberVideos?.let {
            items(items = it.results.chunked(2)){ rowItem ->
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp, 5.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                )
                {
                    for (it in rowItem) {
                        VideoOvalItem(video = it.video, {
                            navController.navigate("player/${it.video.id}")
                        })
                    }
                    if (rowItem.size < 2) {
                        Spacer(modifier = Modifier.weight(1f)) // Fill empty space if only 1 item in the last row
                    }
                }
            }
        }
    }


}