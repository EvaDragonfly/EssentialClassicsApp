package com.memoittech.cuviewtv.screens.SliderScreens

import android.annotation.SuppressLint
import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.google.accompanist.pager.rememberPagerState
import com.memoittech.cuviewtv.R
import com.memoittech.cuviewtv.viewModel.VideosViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.yield
import kotlin.math.absoluteValue

@RequiresApi(Build.VERSION_CODES.S)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@ExperimentalPagerApi
@Composable
fun SliderComponent(
    navController: NavHostController
){

    val videoViewModel : VideosViewModel = viewModel()

    LaunchedEffect(Unit) {
        videoViewModel.getSliderVideosList(5, 0, "position", "")
    }

    val videos = videoViewModel.sliderVideosResponse?.results

    val pagerState = videos?.size?.let {
        rememberPagerState(
        pageCount = it,
        initialPage = 0
    )
    }

    LaunchedEffect(Unit, videos?.size) {
        while (true){
            yield()
            delay(2000)
            pagerState?.animateScrollToPage(
                page = (pagerState.currentPage+1) % (pagerState.pageCount),
                animationSpec = tween(600)
            )
        }
    }


    Column(modifier = Modifier
        .height(372.dp)
        .padding(0.dp, 0.dp, 0.dp, 0.dp)
    ) {
        pagerState?.let { it1 ->
            HorizontalPager(state = it1,
                modifier = Modifier.weight(1f)
                    .padding(0.dp,0.dp,0.dp,0.dp )
            ) { page ->
                Card(modifier = Modifier.graphicsLayer {
                    val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue
                    lerp(
                        start = 0.85f,
                        stop = 1f,
                        fraction = 1f-pageOffset.coerceIn(0f, 1f)
                    ).also { scale ->
                        scaleX = scale
                        scaleY = scale
                    }
                    alpha = lerp(
                        start = 0.5f,
                        stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    )
                }
                    .fillMaxWidth()
                    .align(Alignment.Center)
                    .padding(0.dp, 0.dp, 0.dp, 0.dp),
                ) {

                    Box(modifier = Modifier.fillMaxSize()
                        .background(Color.LightGray)
                        .align(Alignment.Center)
                        .padding(top = 20.dp)
                    ){
                        AsyncImage(
                            modifier = Modifier
                                .height(372.dp)
                                .fillMaxWidth()
                                .graphicsLayer {
                                    renderEffect = RenderEffect.createBlurEffect(
                                        40f,
                                        40f,
                                        Shader.TileMode.CLAMP
                                    ).asComposeRenderEffect()
                                },
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(if (videos[page].has_thumbnail) "https://img.cugate.com/?o=eclass_video&i=${videos[page].id}&s=300" else "https://img.youtube.com/vi/${videos[page].youtube_id}/maxresdefault.jpg")
                                .crossfade(true)
                                .build(),
                            placeholder = painterResource(id= R.drawable.video),
                            error = painterResource(id= R.drawable.video),
                            contentDescription = "Video thumbnail",
                            contentScale = ContentScale.Crop
                        )
                        Box(
                            modifier = Modifier
                                .matchParentSize()
                        ){
                            Column (
                                modifier = Modifier.align(alignment = Alignment.Center)
                            ){
                                Box(
                                    modifier = Modifier
                                        .width(360.dp)
                                        .height(205.dp)
                                        .clickable {
                                            navController.navigate("player/${videos[page].id}/0")
                                        },
                                    contentAlignment = Alignment.BottomEnd
                                ){
                                    AsyncImage(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .clip(RoundedCornerShape(6.dp)),
                                        model = ImageRequest.Builder(LocalContext.current)
                                            .data(if (videos[page].has_thumbnail) "https://img.cugate.com/?o=eclass_video&i=${videos[page].id}&s=300" else "https://img.youtube.com/vi/${videos[page].youtube_id}/maxresdefault.jpg")
                                            .crossfade(true)
                                            .build(),
                                        placeholder = painterResource(id= R.drawable.video),
                                        error = painterResource(id= R.drawable.video),
                                        contentDescription = "Video thumbnail",
                                        contentScale = ContentScale.Crop
                                    )
                                    Image(
                                        painter = painterResource(R.drawable.playericon),
                                        contentDescription = "Play",
                                        modifier = Modifier.padding(15.dp)
                                    )
                                }

                                Text(
                                    text = videos[page].title,
                                    color = Color.White,
                                    fontSize = 16.sp,
                                    maxLines = 1,
                                    textAlign = TextAlign.Center,
                                    fontWeight = FontWeight.W600,
                                    modifier = Modifier
                                        .align(alignment = Alignment.CenterHorizontally)
                                        .width(360.dp)
                                        .padding(5.dp, 3.dp)
                                )
                                Text(
                                    text = videos[page].description.toString(),
                                    color = Color.White,
                                    fontSize = 15.sp,
                                    maxLines = 1,
                                    fontWeight = FontWeight.W400,
                                    fontStyle = FontStyle.Italic,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .align(alignment = Alignment.CenterHorizontally)
                                        .width(360.dp)
                                        .padding(5.dp, 0.dp, 5.dp, 0.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
        pagerState?.let { it1 ->
            HorizontalPagerIndicator(
                pagerState= it1, modifier = Modifier.align(Alignment.CenterHorizontally)
                    .padding(10.dp)
                    .offset(y = (-40).dp),
                activeColor = Color.White,
                inactiveColor = Color.LightGray
            )
        }
    }


}


//@RequiresApi(Build.VERSION_CODES.S)
//@OptIn(ExperimentalPagerApi::class)
//@Preview
//@Composable
//fun prev(){
//    SliderScreen()
//}
