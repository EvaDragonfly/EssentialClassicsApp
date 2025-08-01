package com.memoittech.cuviewtv.screens.SliderScreens

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.memoittech.cuviewtv.ui.theme.DarkBg2
import com.memoittech.cuviewtv.viewModel.AppViewModels


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalPagerApi::class)
@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun OpenScreen (navController : NavHostController, appViewModel: AppViewModels, modifier : Modifier) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(DarkBg2)
    ) {
        SliderComponent(navController)
        LazyColumn(
            modifier = Modifier.padding(5.dp)
        ) {
            item {
                SliderComposersComponent(navController, appViewModel)
            }

            item {
                SliderPerformersComponent(navController, appViewModel)
            }

            item {
                SliderVideosComponent(navController, appViewModel)
            }
        }
    }
}