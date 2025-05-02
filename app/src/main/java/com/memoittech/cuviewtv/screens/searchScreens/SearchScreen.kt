package com.memoittech.cuviewtv.screens.searchScreens


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.memoittech.cuviewtv.R
import com.memoittech.cuviewtv.ui.theme.DarkBg2
import com.memoittech.cuviewtv.ui.theme.GrayBlue
import com.memoittech.cuviewtv.ui.theme.PlaceHolderColor
import com.memoittech.cuviewtv.ui.theme.Rose
import com.memoittech.cuviewtv.ui.theme.Violet


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(navController: NavController, index : Int){

    var text by remember { mutableStateOf("") }

    var searchItems = remember {
        mutableStateListOf(
            Pair(0,"Performers"),
            Pair(1, "Composers"),
            Pair(2, "Tracks"),
            Pair(3, "Videos"),
        )
    }

    var currentComponent by remember { mutableStateOf(0) }
    var selectedOption by remember { mutableStateOf(searchItems [index]) }


    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(color = DarkBg2)
            .padding(WindowInsets.systemBars.asPaddingValues())
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = DarkBg2)
                .padding(20.dp, 10.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Image(
                    modifier = Modifier.clickable { navController.popBackStack() },
                    painter = painterResource(R.drawable.backarrow),
                    contentDescription = "go back"
                )
                Image(painter = painterResource(R.drawable.shareicon), contentDescription = "share")
            }

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
                                        currentComponent = it.first },
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


            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.BottomEnd
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(30.dp)
                        .padding(10.dp, 9.dp)
                        .background(Violet, RoundedCornerShape(6.dp))
                )


                TextField(
                    value = text,
                    onValueChange = { text = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                        .height(46.dp)
                        .align(Alignment.Center)
                        .clip(RoundedCornerShape(6.dp))
                        .background(Color(0xFF2C3C5C)),
                    placeholder = {
                        Text(
                            text = "Search...",
                            color = PlaceHolderColor
                        )
                    },
                    singleLine = true,
                    colors = TextFieldDefaults.textFieldColors(
//                        backgroundColor = Violet,
                        textColor = GrayBlue,
                    ),
                    leadingIcon = {
                        Image(
                            painter = painterResource(R.drawable.whitesearch),
                            contentDescription = "Search Icon",
                            modifier = Modifier.padding(0.dp).size(20.dp)
                        )
                    },
                    trailingIcon = {
                        if (text.isNotEmpty()) {
                            IconButton(onClick = { text = "" }) {
                                Image(
                                    painter = painterResource(R.drawable.crossicon),
                                    contentDescription = "Cancel"
                                )
                            }
                        }
                    }
                )

            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color(0x8054609C), // 50% opacity
                                Color(0x0054609C)
                            )
                        )
                    )
            )


            when(currentComponent){
            0 -> PerformersComponent(navController, text )
            1 -> ComposersComponent(navController, text)
            2 -> TracksComponent(navController, text)
            3 -> VideosComponent(navController, text)

            }
        }

    }

}



//@Preview
//@Composable
//fun prev(){
//    SearchScreen()
//}