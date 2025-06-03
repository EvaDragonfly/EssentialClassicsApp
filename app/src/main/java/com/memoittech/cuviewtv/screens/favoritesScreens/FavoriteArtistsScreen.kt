package com.memoittech.cuviewtv.screens.favoritesScreens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.memoittech.cuviewtv.R
import com.memoittech.cuviewtv.components.MemberHorizontalItem
import com.memoittech.cuviewtv.components.Separator
import com.memoittech.cuviewtv.ui.theme.DarkBg2
import com.memoittech.cuviewtv.ui.theme.GrayBlue
import com.memoittech.cuviewtv.ui.theme.Violet
import com.memoittech.cuviewtv.viewModel.MembersViewModel


@Composable
fun FavoriteArtistsScreen(
    navController : NavController
) {

    val membersViewModel : MembersViewModel = viewModel()

    var ordering by remember { mutableStateOf("-created_at") }

    val listState = rememberLazyListState()

    val favoritemembers = membersViewModel.favouriteMembers

    fun onMemberClick(id: Int){
        navController.navigate("member_details/${id}")
    }

    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemIndex }
            .collect { index ->
                if (index >= membersViewModel.favouriteMembers.size - 15 && !membersViewModel.isFavMembersLoading) {
                    membersViewModel.getFavoriteMembers(ordering)
                }
            }
    }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBg2)
            .padding(
                WindowInsets.systemBars.asPaddingValues()
            )
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp, 20.dp, 10.dp, 0.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            Image(
                modifier = Modifier.clickable {
                    navController.popBackStack()
                },
                painter = painterResource(R.drawable.backarrow),
                contentDescription = "go back"
            )
        }

        Text(
            text = "Artists",
            modifier = Modifier
                .padding(20.dp, 10.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Start,
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.W600
        )

        Separator()

        favoritemembers.let {
            LazyColumn (
                state = listState
            ){
                items(items = it){item ->
                    MemberHorizontalItem(
                        item.member
                    ) {
                        onMemberClick(item.member.id)
                    }
                }
                item {
                    if (membersViewModel.isFavMembersLoading){
                        androidx.compose.material3.Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "Loading...",
                            textAlign = TextAlign.Center,
                            color = GrayBlue
                        )
                    }
                }
            }
        }

    }
}

//@Preview
//@Composable
//fun prevFav(){
//    FavoriteArtistsScreen()
//}