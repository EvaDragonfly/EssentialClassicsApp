package com.memoittech.cuviewtv.screens.authScreens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.memoittech.cuviewtv.R
import com.memoittech.cuviewtv.ui.theme.DarkBg2
import com.memoittech.cuviewtv.ui.theme.Violet

@Composable
fun TermsAndConditionsScreen(
    navController: NavHostController
){

    val context = LocalContext.current
    val policyText = remember {
        context.resources.openRawResource(R.raw.privacy_policy)
            .bufferedReader().use { it.readText() }
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .background(color = DarkBg2)
        .padding(25.dp)
    ) {
//        HeadingTextComponent(value = stringResource(id = R.string.terms_and_conditions_header))
        Image(
            painter = painterResource(id = R.drawable.backarrow),
            contentDescription = "",
            modifier = Modifier
                .padding(bottom = 15.dp)
                .clickable {
                navController.popBackStack()
            }
        )
        Text(
            modifier = Modifier.fillMaxWidth()
                .padding(bottom = 20.dp),
            text = "privacy_policy",
            fontSize = 20.sp,
            color = Violet,
            fontWeight = FontWeight.W500,
            textAlign = TextAlign.Center
        )
        LazyColumn(
            modifier = Modifier.fillMaxSize()
//                .background(color = DarkBg2)
        ) {
            item {
                Text(
                    text = policyText,
                    fontSize = 14.sp,
                    color = Violet,

                )
            }
        }
    }

}
//
//@Preview
//@Composable
//fun termsPrev(){
//    TermsAndConditionsScreen()
//}