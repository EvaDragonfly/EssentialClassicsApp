package com.memoittech.cuviewtv.screens.detailScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.memoittech.cuviewtv.ui.theme.DarkBg2
import com.memoittech.cuviewtv.ui.theme.GrayBlueLight

@Composable
fun AboutMemberComponent( text : String) {
    LazyColumn {
        item {
            Text(
                modifier = Modifier
                    .background(DarkBg2)
                    .padding(20.dp),
                text = text,
                color = GrayBlueLight,
                fontSize = 13.sp,
                fontWeight = FontWeight.W400
            )
        }
    }
}


@Preview
@Composable
fun prev(){
    AboutMemberComponent("Wolfgang Amadeus Mozart, born on January 27, 1756, in Salzburg, Austria, was a prolific and influential composer of the Classical period. He was the youngest of seven children born to Leopold and Anna Maria Mozart, though only he and his sister Maria Anna, nicknamed \"Nannerl,\" survived infancy. His father, Leopold, was a successful composer and violinist, who recognized and nurtured Wolfgang's prodigious musical talents from an early age. By the age of five, Mozart was already composing and performing before European royalty.\n\nMozart's early life was marked by extensive tours across Europe, where he and his sister performed as child prodigies. These tours exposed him to a variety of musical styles and influential composers, such as Johann Christian Bach, which greatly impacted his development. Despite his early success, Mozart struggled to find stable employment and financial security throughout his life.\n\nIn 1781, after being dismissed from his position at the Salzburg court, Mozart moved to Vienna, where he achieved fame but continued to face financial difficulties. During his time in Vienna, he composed many of his most celebrated works, including operas such as \"The Marriage of Figaro,\" \"Don Giovanni,\" and \"The Magic Flute,\" as well as his final three symphonies and the unfinished \"Requiem.\"\n\nMozart's music is renowned for its melodic beauty, formal elegance, and rich harmonic and textural complexity. He composed over 800 works across virtually every genre of his time, leaving an indelible mark on Western classical music. Despite his untimely death on December 5, 1791, at the age of 35, Mozart's legacy continues to influence and inspire musicians and composers worldwide.")
}