package com.memoittech.cuviewtv.screens.detailScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.memoittech.cuviewtv.components.HorizontalTrackItem
import com.memoittech.cuviewtv.components.VerticalTrackItem
import com.memoittech.cuviewtv.ui.theme.Violet
import com.memoittech.cuviewtv.viewModel.MembersViewModel
import com.memoittech.cuviewtv.viewModel.TracksViewModel

@Composable
fun MemberTracksComponent( navController: NavController, id: Int){

    val viewModel : MembersViewModel = viewModel()

    val tracksViewModel : TracksViewModel = viewModel()

    LaunchedEffect(Unit) {
        viewModel.getMemberTracks(id)
    }

    val memberTracks = viewModel.memberTracksResponse

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(1.dp),
        modifier = Modifier.fillMaxWidth()
            .background(Violet)
    ) {
        memberTracks?.let {
            items(items = memberTracks.results){item ->
                VerticalTrackItem(
                    track = item.track,
                    0,
                    {
                        navController.navigate("track_details/${item.track.id}")
                    },
                    {
                        navController.navigate(
                            "track_details/${item.track.id}"
                        )
                    },
                    {
                        tracksViewModel.addFavoriteTrack(item.track.id, false)
                    }
                )
            }
        }
    }

}

