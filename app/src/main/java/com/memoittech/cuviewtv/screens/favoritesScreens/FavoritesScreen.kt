package com.memoittech.cuviewtv.screens.favoritesScreens

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.memoittech.cuviewtv.R
import com.memoittech.cuviewtv.components.FavoritesItem
import com.memoittech.cuviewtv.components.Separator
import com.memoittech.cuviewtv.ui.theme.DarkBg2
import com.memoittech.cuviewtv.ui.theme.Rose
import com.memoittech.cuviewtv.ui.theme.Violet
import com.memoittech.cuviewtv.viewModel.AuthViewModel
import com.memoittech.cuviewtv.viewModel.FavoriteCountViewModel

@Composable
fun FavoritesScreen(navController: NavController) {

    val authViewModel : AuthViewModel = viewModel()

    val favoriteViewModel : FavoriteCountViewModel = viewModel()

    val context = LocalContext.current

    val prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)

    val email = prefs.getString("email", null)

    LaunchedEffect(Unit) {
        favoriteViewModel.getFavoriteCount()
    }

    val favoriteCounts = favoriteViewModel.favoriteCountResponse?.data

    val user = authViewModel.user

    fun logoutHandler(){
        authViewModel.logout(navController)
    }

    Column (
        modifier = Modifier
            .background(DarkBg2)
            .padding(WindowInsets.systemBars.asPaddingValues())
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            Box(
                modifier = Modifier
                    .size(46.dp)
                    .background(
                       color =  Rose,
                        shape = RoundedCornerShape(23.dp)
                    ),
                contentAlignment = Alignment.Center
            ){
                Text(
                    text = email?.take(2).toString(),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W500,
                    color = Color.White,
                    modifier = Modifier
                        .padding(10.dp)
                )

            }

            email?.let {
                Text(
                    text = it,
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W500
                )
            }

            Button(
                onClick = {logoutHandler()},
                modifier = Modifier
                    .width(103.dp)
                    .height(48.dp),
                contentPadding = PaddingValues(),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                shape = RoundedCornerShape(6.dp) // Slightly rounded corners
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(6.dp)) // Match the same radius
                        .background(Violet),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Log out",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }



        }
        Text(
            text = "Favorites",
            modifier = Modifier
                .padding(20.dp, 10.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Start,
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.W600
        )

        Separator()
        favoriteCounts?.let {
            Column(modifier = Modifier.fillMaxWidth()) {
                FavoritesItem(
                    R.drawable.trackiconwhite,
                    "Tracks",
                    favoriteCounts.tracks,
                    {
                        navController.navigate("favorite_tracks")
                    }
                )
                FavoritesItem(
                    R.drawable.videosiconwhite,
                    "Videos",
                    favoriteCounts.videos,
                    {
                        navController.navigate("favorite_videos")
                    }
                )
                FavoritesItem(
                    R.drawable.artistsiconwhite,
                    "Artists",
                    favoriteCounts.members,
                    {
                        navController.navigate("favorite_members")
                    }
                )
            }
        }
    }
}

//@Preview
//@Composable
//fun favoritePrev(){
//    FavoritesScreen()
//}