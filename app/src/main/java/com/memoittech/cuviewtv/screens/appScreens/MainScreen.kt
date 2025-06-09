package com.memoittech.cuviewtv.screens.appScreens

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Scaffold
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.memoittech.cuviewtv.navigation.BottomBarScreen
import com.memoittech.cuviewtv.screens.MoodScreens.MoodsScreen
import com.memoittech.cuviewtv.screens.SliderScreens.OpenScreen
import com.memoittech.cuviewtv.screens.favoritesScreens.FavoritesScreen
import com.memoittech.cuviewtv.screens.searchScreens.SearchScreen
import com.memoittech.cuviewtv.ui.theme.DarkBg2
import com.memoittech.cuviewtv.ui.theme.GrayBlue
import com.memoittech.cuviewtv.viewModel.AppViewModels


@RequiresApi(Build.VERSION_CODES.S)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen(
    navController: NavHostController,
    appViewModel: AppViewModels,
    startTab: String
){

    var currentTab by rememberSaveable { mutableStateOf(startTab) }

    Scaffold(
        bottomBar = {
            BottomBar(currentTab = currentTab) {
                selectedTab ->
                currentTab = selectedTab
                navController.navigate("main/$selectedTab") {
                    launchSingleTop = true
                }
            }
        }
    )
    {
        padding ->
        when (currentTab) {
            "slider" -> OpenScreen(navController, appViewModel, Modifier.padding(padding))
            "search" -> SearchScreen(navController, appViewModel, Modifier.padding(padding))
            "moods" -> MoodsScreen(navController, Modifier.padding(padding))
            "favorites" -> FavoritesScreen(navController, Modifier.padding(padding))
        }
    }
}

@Composable
fun BottomBar(
    currentTab : String,
    onTabSelected: (String) -> Unit
){
    val screens = listOf(
        BottomBarScreen.Slider,
        BottomBarScreen.Search,
        BottomBarScreen.Moods,
        BottomBarScreen.Favorite
    )

    BottomNavigation(
        backgroundColor = Color.White,
        contentColor = Color.Black,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding())
    ) {
        screens.forEach { screen ->
            BottomNavigationItem(
                selected = currentTab == screen.route,
                onClick = { onTabSelected(screen.route) },
                icon = {
                    Icon(
                        painter =  painterResource(id = screen.icon),
                        contentDescription = screen.title,
                        tint = if (currentTab == screen.route) DarkBg2 else GrayBlue
                    )
                },
                label = {
                    Text(
                        text = screen.title,
                        color = if (currentTab == screen.route) DarkBg2 else GrayBlue,
                        fontWeight = FontWeight.W500,
                        fontSize = 13.sp
                    )
                },
            )
        }
    }

}
