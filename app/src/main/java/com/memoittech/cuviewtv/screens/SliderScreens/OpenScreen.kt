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
import com.google.accompanist.pager.ExperimentalPagerApi
import com.memoittech.cuviewtv.ui.theme.DarkBg2


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalPagerApi::class)
@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun OpenScreen (navController : NavController) {


    Scaffold(

        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(DarkBg2)
            ) {
                SliderComponent(navController)
                LazyColumn(
                    modifier = Modifier.padding(5.dp)
                ) {
                    item {
                        SliderComposersComponent(navController)
                    }

                    item {
                        SliderPerformersComponent(navController)
                    }

                    item {
                        SliderVideosComponent(navController)
                    }
                }
            }
        }
    )
}