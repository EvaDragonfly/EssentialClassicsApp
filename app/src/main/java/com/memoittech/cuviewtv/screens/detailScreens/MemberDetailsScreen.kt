package com.memoittech.cuviewtv.screens.detailScreens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.memoittech.cuviewtv.R
import com.memoittech.cuviewtv.components.Separator
import com.memoittech.cuviewtv.ui.theme.DarkBg2
import com.memoittech.cuviewtv.ui.theme.GrayBlueLight
import com.memoittech.cuviewtv.viewModel.MembersViewModel


@Composable
fun MemberDetailsScreen(navController: NavController, id : Int){

    val memberViewModel : MembersViewModel = viewModel()

    val memberDetails = memberViewModel.membersResponse

    var currentComponent by remember { mutableStateOf(0) }

    LaunchedEffect(key1 = id) {
        memberViewModel.getMemberDetails(id)
    }

    var member by remember { mutableStateOf(memberDetails) }

    LaunchedEffect(memberDetails) {
        member = memberDetails
    }

    var menuItems = remember {
        mutableStateListOf(
            Pair(0,"About"),
            Pair(1, "Tracks"),
            Pair(2, "Videos")
        )
    }

    var selectedOption by remember { mutableStateOf(menuItems [0]) }

    fun onFavouriteClick(){
        if(member?.is_favorite == true){
            memberViewModel.addFavoriteMember(id, true)
        } else {
            memberViewModel.addFavoriteMember(id, false)
        }
        member = member?.copy(is_favorite = !member?.is_favorite!!)
    }

    Surface(
        modifier = Modifier.fillMaxSize()
        .background(DarkBg2)
        ){
        member?.let {
            Column(
                modifier = Modifier
                    .background(DarkBg2)
                    .padding(WindowInsets.systemBars.asPaddingValues())
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        modifier = Modifier.clickable { navController.popBackStack() },
                        painter = painterResource(R.drawable.backarrow),
                        contentDescription = "go back"
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Image(
                            modifier = Modifier.clickable { onFavouriteClick() },
                            painter = painterResource(
                                if (member?.is_favorite == true)
                                    R.drawable.favoritewhitefill
                                else
                                    R.drawable.favoritewhite
                            ),
                            contentDescription = "Add Favorite"
                        )
                        Image(
                            painter = painterResource(R.drawable.shareicon),
                            contentDescription = "share"
                        )
                    }

                }

                Text(
                    modifier = Modifier.padding(20.dp, 10.dp),
                    text = member!!.title,
                    fontSize = 18.sp,
                    color = Color.White,
                    fontWeight = FontWeight.W700,
                    maxLines = 1
                )
                Image(
                    painter = painterResource(id = R.drawable.some_image),
                    contentDescription = "member image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxWidth()
                        .aspectRatio(1.98F, true)
                )


                LazyRow(
                    modifier = Modifier.padding(30.dp, 10.dp, 30.dp, 0.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    items(items = menuItems) { item ->
                        Box(
                            modifier = Modifier
                                .drawBehind {
                                    val strokeWidth = 2.dp.toPx()
                                    val y = size.height - strokeWidth / 2
                                    drawLine(
                                        color = if (item == selectedOption) GrayBlueLight else Color.Transparent,
                                        start = Offset(0f, y),
                                        end = Offset(size.width, y),
                                        strokeWidth = strokeWidth
                                    )
                                }
                                .clickable {
                                    selectedOption = item
                                    currentComponent = item.first
                                }
                        ) {
                            Text(
                                modifier = Modifier.padding(10.dp),
                                text = item.second,
                                color = GrayBlueLight,
                                fontWeight = FontWeight.W600,
                                fontSize = 15.sp
                            )
                        }

                    }
                }


                Separator()

                when (currentComponent) {
                    0 -> AboutMemberComponent(member?.biography_text.toString())
                    1 -> member?.id?.let { MemberTracksComponent(navController, it) }
                    2 -> member?.id?.let { MemberVideosComponent(navController, it) }
                }

            }
        }
    }
}

