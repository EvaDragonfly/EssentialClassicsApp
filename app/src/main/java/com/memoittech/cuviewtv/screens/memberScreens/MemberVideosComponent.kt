package com.memoittech.cuviewtv.screens.memberScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.memoittech.cuviewtv.components.VideoOvalItem
import com.memoittech.cuviewtv.ui.theme.DarkBg2
import com.memoittech.cuviewtv.ui.theme.GrayBlue
import com.memoittech.cuviewtv.ui.theme.Violet
import com.memoittech.cuviewtv.viewModel.MembersViewModel

@Composable
fun MemberVideosComponent(navController: NavHostController, id: Int){

    val viewModel : MembersViewModel = viewModel()

    LaunchedEffect(Unit) {
        viewModel.getMemberVideos(id)
    }

    val listState = rememberLazyListState()

    val memberVideos = viewModel.membersVideos

    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemIndex }
            .collect { index ->
                if (index >= viewModel.membersVideos.size/2 - 10  && !viewModel.isMemberVideosLoading) {
                    viewModel.getMemberVideos(id)
                }
            }
    }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        LazyColumn(
            state = listState,
            modifier = Modifier
                .background(DarkBg2)
                .padding(0.dp, 10.dp),
            verticalArrangement = Arrangement.Center
        ) {
            items(items = memberVideos.chunked(2)){ rowItem ->
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp, 5.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                )
                {
                    for (it in rowItem) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .padding(5.dp)
                        ){
                            VideoOvalItem(id = it.id, youtube_id = it.youtube_id, title = it.title, has_thumbnail = it.has_thumbnail) {
                                navController.navigate("player/${it.id}/${it.starts_at}")
                            }
                        }
                    }
                    if (rowItem.size < 2) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .padding(10.dp)// Same padding as VideoOvalItem
                        )
                    }
                }
            }
            item {
                if (viewModel.isMemberVideosLoading){
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