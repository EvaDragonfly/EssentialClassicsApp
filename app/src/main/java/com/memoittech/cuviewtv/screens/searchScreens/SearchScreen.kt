package com.memoittech.cuviewtv.screens.searchScreens


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.memoittech.cuviewtv.components.SearchComponent
import com.memoittech.cuviewtv.components.Separator
import com.memoittech.cuviewtv.ui.theme.DarkBg2
import com.memoittech.cuviewtv.ui.theme.Rose
import com.memoittech.cuviewtv.ui.theme.Violet
import com.memoittech.cuviewtv.viewModel.AppViewModels


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(navController: NavHostController, appViewModel: AppViewModels, modifier: Modifier){

//    val text = rememberSaveable { mutableStateOf("") }

    var searchItems = remember {
        mutableStateListOf(
            Pair(0,"Performers"),
            Pair(1, "Composers"),
            Pair(2, "Tracks"),
            Pair(3, "Videos"),
        )
    }

    val index = appViewModel.index

    var currentComponent = index
    var selectedOption = searchItems.getOrNull(index) ?: 0

    Surface(
        modifier = modifier
            .fillMaxSize()
            .background(color = DarkBg2)
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = DarkBg2)
                .padding(10.dp, 50.dp, 10.dp, 0.dp)
        ) {
            LazyColumn(
                modifier = Modifier.padding(0.dp, 10.dp)
            ) {
                items(items = searchItems.chunked(2)){ rowItem ->
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp)
                    )
                    {
                        for (it in rowItem) {
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(3.dp, 0.dp)
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(color = if (it == selectedOption) Rose else Violet)
                                    .clickable {
                                        selectedOption = it;
                                        currentComponent = it.first
                                        appViewModel.onIndexChanged(it.first)
                                               },
                                contentAlignment = Alignment.Center
                            ){
                                Text(
                                    text = it.second.uppercase(),
                                    fontWeight = FontWeight.W400,
                                    fontSize = 16.sp,
                                    color = Color.White,
                                    modifier = Modifier.padding(10.dp)
                                )
                            }
                        }
                        if (rowItem.size < 2) {
                            Spacer(modifier = Modifier.weight(1f)) // Fill empty space if only 1 item in the last row
                        }
                    }
                }
            }

            SearchComponent(
                appViewModel.query,
                onTextChange = { appViewModel.onQueryChanged(it) },
            )

            Separator()


            when(currentComponent){
            0 -> PerformersComponent(navController, appViewModel )
            1 -> ComposersComponent(navController, appViewModel )
            2 -> TracksComponent(navController, appViewModel)
            3 -> VideosComponent(navController, appViewModel)
            }
        }

    }

}



//@Preview
//@Composable
//fun prev(){
//    SearchScreen()
//}