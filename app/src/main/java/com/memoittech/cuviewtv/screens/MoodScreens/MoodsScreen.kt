package com.memoittech.cuviewtv.screens.MoodScreens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun MoodsScreen(navController: NavController){


    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        ColorWheelExample(navController)
    }

}