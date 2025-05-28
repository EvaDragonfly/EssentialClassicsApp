package com.memoittech.cuviewtv.navigation


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.accompanist.pager.ExperimentalPagerApi
import com.memoittech.cuviewtv.R
import com.memoittech.cuviewtv.screens.SliderScreens.OpenScreen
import com.memoittech.cuviewtv.screens.MoodScreens.MoodsScreen
import com.memoittech.cuviewtv.screens.favoritesScreens.FavoritesScreen
import com.memoittech.cuviewtv.screens.searchScreens.SearchScreen
import com.memoittech.cuviewtv.viewModel.AppViewModels


@RequiresApi(Build.VERSION_CODES.S)
@OptIn(ExperimentalPagerApi::class)
@Composable
fun BottomNavGraph(bottomNavController: NavHostController, rootNavController : NavController, appViewModel: AppViewModels,
                   modifier: Modifier = Modifier){
    NavHost(navController = bottomNavController,
        startDestination = BottomBarScreen.Slider.route,
        modifier = modifier
        ){
            composable(route = BottomBarScreen.Slider.route){
                OpenScreen(rootNavController, appViewModel)
            }
            composable(route = BottomBarScreen.Moods.route){
                MoodsScreen(rootNavController)
            }
            composable(route = BottomBarScreen.Search.route){
                SearchScreen(rootNavController, appViewModel)
            }
            composable(route = BottomBarScreen.Favorite.route){
                FavoritesScreen(rootNavController)
            }
    }
}

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: Int,
) {
    object Slider : BottomBarScreen(
        route = "slider",
        title = "Main",
        icon = R.drawable.main,
    )
    object Moods : BottomBarScreen(
        route = "moods",
        title = "Moods",
        icon = R.drawable.moods,
    )
    object Search : BottomBarScreen(
        route = "search",
        title = "Search",
        icon = R.drawable.search,
    )
    object Favorite : BottomBarScreen(
        route = "favorites",
        title = "Favorites",
        icon = R.drawable.collection,
    )
}