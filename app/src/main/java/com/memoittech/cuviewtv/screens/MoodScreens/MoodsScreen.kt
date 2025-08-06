package com.memoittech.cuviewtv.screens.MoodScreens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.memoittech.cuviewtv.viewModel.AppViewModels

@Composable
fun MoodsScreen(navController: NavHostController, appViewModels: AppViewModels, modifier: Modifier){


    Surface(
        modifier = modifier.fillMaxSize()
    ) {
        ColorWheelExample(navController, appViewModels)
    }

}