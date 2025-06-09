package com.memoittech.cuviewtv.screens.MoodScreens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController

@Composable
fun MoodsScreen(navController: NavHostController, modifier: Modifier){


    Surface(
        modifier = modifier.fillMaxSize()
    ) {
        ColorWheelExample(navController)
    }

}