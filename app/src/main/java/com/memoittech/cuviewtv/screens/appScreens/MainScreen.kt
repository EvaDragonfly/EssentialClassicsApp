package com.memoittech.cuviewtv.screens.appScreens

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Scaffold
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.memoittech.cuviewtv.navigation.BottomBarScreen
import com.memoittech.cuviewtv.navigation.BottomNavGraph
import com.memoittech.cuviewtv.ui.theme.DarkBg2
import com.memoittech.cuviewtv.ui.theme.GrayBlue


@RequiresApi(Build.VERSION_CODES.S)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen(navController: NavHostController){

    val bottomNavController = rememberNavController()

    Scaffold(bottomBar = {
        BottomBar(navController = bottomNavController)
    })
    {
        innerPadding ->
        BottomNavGraph(
            bottomNavController = bottomNavController,
            rootNavController = navController,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun BottomBar(navController: NavHostController){
    val screens = listOf(
        BottomBarScreen.Slider,
        BottomBarScreen.Moods,
        BottomBarScreen.Search,
        BottomBarScreen.Favorite
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    BottomNavigation(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp) // Set your desired height
            .background(Color.White),
        backgroundColor = Color.White, // Optional, to be explicit
        elevation = 8.dp
    ) {
        screens.forEach { screen ->
            AddItem(screen = screen, currentDestination = currentDestination, navController = navController)
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen : BottomBarScreen,
    currentDestination: NavDestination?,
    navController: NavHostController
){

    val selected = currentDestination?.hierarchy?.any {
        it.route == screen.route
    } == true

    BottomNavigationItem(
        selected = selected,
        label = {
            Text(
                text = screen.title,
                color = if (selected) DarkBg2 else GrayBlue,
                fontWeight = FontWeight.W500,
                fontSize = 16.sp
            )
        },
        icon = {
            Icon(
                painter =  painterResource(id = screen.icon),
                contentDescription = screen.title,
                tint = if (selected) DarkBg2 else GrayBlue
            )
        },
        onClick = {
            navController.navigate(screen.route)
        }
    )
}