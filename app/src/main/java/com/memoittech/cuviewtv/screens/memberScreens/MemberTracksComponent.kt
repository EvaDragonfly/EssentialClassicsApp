package com.memoittech.cuviewtv.screens.memberScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import com.memoittech.cuviewtv.components.VerticalTrackItem
import com.memoittech.cuviewtv.ui.theme.GrayBlue
import com.memoittech.cuviewtv.ui.theme.Violet
import com.memoittech.cuviewtv.viewModel.MembersViewModel

@Composable
fun MemberTracksComponent( navController: NavHostController, id: Int){

    val viewModel : MembersViewModel = viewModel()

    val listState = rememberLazyListState()

    LaunchedEffect(Unit) {
        viewModel.getMemberTracks(id)
    }

    val memberTracks = viewModel.memberTracks

    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemIndex }
            .collect { index ->
                if (index >= viewModel.memberTracks.size - 10 && !viewModel.isMemberTracksLoading) {
                    viewModel.getMemberTracks(id)
                }
            }
    }

    Column (
        modifier = Modifier.fillMaxWidth()
    ){

        LazyColumn(
            state = listState,
            verticalArrangement = Arrangement.spacedBy(1.dp),
            modifier = Modifier.fillMaxWidth()
                .background(Violet)
        ) {
            items(items = memberTracks){item ->
                VerticalTrackItem(
                    track = item,
                    0,
                    {
                        navController.navigate("track_details/${item.id}")
                    },
                    {
                        navController.navigate(
                            "track_details/${item.id}"
                        )
                    }
                )
            }
            item {
                if (viewModel.isMemberTracksLoading){
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

