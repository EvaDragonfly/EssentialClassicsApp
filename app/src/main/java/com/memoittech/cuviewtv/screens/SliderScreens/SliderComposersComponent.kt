package com.memoittech.cuviewtv.screens.SliderScreens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.memoittech.cuviewtv.R
import com.memoittech.cuviewtv.components.MemberVerticalItem
import com.memoittech.cuviewtv.ui.theme.GrayBlue
import com.memoittech.cuviewtv.viewModel.MembersViewModel

@Composable
fun SliderComposersComponent(navController: NavController){

    val viewModels : MembersViewModel = viewModel()

    LaunchedEffect(Unit) {
        viewModels.getComposerList(1,"position", "", 1)
    }

    val composers = viewModels.composers

    Column (modifier = Modifier.fillMaxWidth()){
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp, 5.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = "Composers",
                fontSize = 18.sp,
                fontWeight = FontWeight.W600,
                color = Color.White
            )
            Row(
                modifier = Modifier
                    .clickable { navController.navigate("search/${1}") },
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    modifier = Modifier
                        .padding(10.dp, 0.dp),
                    text = "Show All",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.W300,
                    color = GrayBlue
                )
                Image(
                    painter = painterResource(R.drawable.rightarrow),
                    contentDescription = "Show All"
                )
            }
        }
        composers?.let {
            LazyRow (
                modifier = Modifier.padding(0.dp, 15.dp),
//                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ){
                items(items = it){item ->
                    MemberVerticalItem(item, {
                        navController.navigate("member_details/${item.id}")
                    })
                }
            }

        }
    }

}