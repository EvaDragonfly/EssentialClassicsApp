package com.memoittech.cuviewtv.screens.memberScreens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.memoittech.cuviewtv.ui.theme.DarkBg2
import com.memoittech.cuviewtv.ui.theme.GrayBlueLight

@Composable
fun AboutMemberComponent( text : String) {

    Log.d("MY_TAG", text)

    LazyColumn {
        item {
            Text(
                modifier = Modifier
                    .background(DarkBg2)
                    .padding(20.dp),
                text = if (text == "null") "No Info" else text,
                color = GrayBlueLight,
                fontSize = 13.sp,
                fontWeight = FontWeight.W400
            )
        }
    }
}

