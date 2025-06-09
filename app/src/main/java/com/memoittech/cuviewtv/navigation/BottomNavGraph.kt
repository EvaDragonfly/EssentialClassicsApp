package com.memoittech.cuviewtv.navigation

import com.memoittech.cuviewtv.R


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
    object Search : BottomBarScreen(
        route = "search",
        title = "Search",
        icon = R.drawable.search,
    )
    object Moods : BottomBarScreen(
        route = "moods",
        title = "Moods",
        icon = R.drawable.moods,
    )
    object Favorite : BottomBarScreen(
        route = "favorites",
        title = "Favorites",
        icon = R.drawable.collection,
    )
}