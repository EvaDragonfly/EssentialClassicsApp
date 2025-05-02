package com.memoittech.cuviewtv.screens.authScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.memoittech.cuviewtv.R
import com.memoittech.cuviewtv.components.HeadingTextComponent
import com.memoittech.cuviewtv.ui.theme.bgColor

@Composable
fun TermsAndConditionsScreen(navController: NavController){
    Surface(modifier = Modifier
        .fillMaxSize()
        .background(color = bgColor)
        .padding(16.dp)
    ) {
        HeadingTextComponent(value = stringResource(id = R.string.terms_and_conditions_header))
    }

}