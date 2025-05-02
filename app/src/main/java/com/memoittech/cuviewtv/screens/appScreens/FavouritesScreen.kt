package com.memoittech.cuviewtv.screens.appScreens

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.memoittech.cuviewtv.components.ButtonComponent
import com.memoittech.cuviewtv.components.CustomDialog
import com.memoittech.cuviewtv.components.HorizontalTrackItem
import com.memoittech.cuviewtv.components.HorizontalVideoItem
import com.memoittech.cuviewtv.components.MemberHorizontalItem
import com.memoittech.cuviewtv.components.ValidationMessage
import com.memoittech.cuviewtv.ui.theme.Violet
import com.memoittech.cuviewtv.viewModel.AuthViewModel
import com.memoittech.cuviewtv.viewModel.MembersViewModel
import com.memoittech.cuviewtv.viewModel.TracksViewModel
import com.memoittech.cuviewtv.viewModel.VideosViewModel


@Composable
fun FavouritesScreen(navController: NavController){

    val context = LocalContext.current

    val sharedPreferences = remember {
        context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
    }

    val email = sharedPreferences.getString("email", "")

    var showDialog by remember { mutableStateOf(false) }

    var alertDialog by remember { mutableStateOf(false) }

    var alertText by remember { mutableStateOf("") }

    val authViewModel : AuthViewModel = viewModel()

    val favoriteMembersViewModel : MembersViewModel = viewModel()

    val favoriteTracksViewModel : TracksViewModel = viewModel()

    val favoriteVideosViewModel : VideosViewModel = viewModel()

    fun logoutHandler(){
        authViewModel.logout(navController)
    }

    LaunchedEffect(key1 = navController) {
        favoriteTracksViewModel.getFavoriteTracks(0, 0, "created_at")
        favoriteMembersViewModel.getFavoriteMembers(0, 0, "created_at")
        favoriteVideosViewModel.getFavoriteVideos(0, 0, "created_at")
    }

    Surface {
        Column (modifier = Modifier.fillMaxSize()
            .padding(WindowInsets.systemBars.asPaddingValues())
            .background(color = Color.Green)){
            Spacer(modifier = Modifier.padding(20.dp))

            Text("Favourites Page")

            Spacer(modifier = Modifier.padding(20.dp))

            email?.let { Text(it) }

            Spacer(modifier = Modifier.padding(20.dp))

            ButtonComponent("Logout", { logoutHandler() }, Violet)

            Spacer(modifier = Modifier.padding(20.dp))

            ButtonComponent("reset_password", { showDialog = true }, Violet)

            Spacer(modifier = Modifier.padding(20.dp))

            CustomDialog(showDialog, {showDialog = false}, { showDialog = false; alertDialog = true }, { newValue -> alertText = newValue.toString() })

            ValidationMessage(alertText, alertDialog, onDissmis = {alertDialog = false}, onClick = {alertDialog = false} )

            Spacer(modifier = Modifier.padding(20.dp))

            LazyRow {
                favoriteTracksViewModel.favouriteTracksResponse?.let {
                    items(items = it.results){ it->
                        HorizontalTrackItem(track = it.track, { navController.navigate("track_details/${it.track.id}")})
                    }
                }
            }

            Spacer(modifier = Modifier.padding(20.dp))

            LazyRow {
                favoriteVideosViewModel.favouriteVideosResponse?.let {
                    items(items = it.results){ it->
                        HorizontalVideoItem(video = it.video, { navController.navigate("player/${it.video.id}")})
                    }
                }
            }

            Spacer(modifier = Modifier.padding(20.dp))

            LazyColumn {
                favoriteMembersViewModel.favouriteMembersResponse?.let {
                    items(items = it.results){ it->
                        MemberHorizontalItem(member = it.member, { navController.navigate("member_details/${it.member.id}")})
                    }
                }
            }
        }
    }

}