package com.memoittech.cuviewtv.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.memoittech.cuviewtv.R
import com.memoittech.cuviewtv.model.Member
import com.memoittech.cuviewtv.model.Member_title
import com.memoittech.cuviewtv.model.Track
import com.memoittech.cuviewtv.model.Video
import com.memoittech.cuviewtv.model.VideoTrack
import com.memoittech.cuviewtv.ui.theme.DarkBg2
import com.memoittech.cuviewtv.ui.theme.GrayBlue
import com.memoittech.cuviewtv.ui.theme.GrayBlueLight
import com.memoittech.cuviewtv.ui.theme.Violet
import com.memoittech.cuviewtv.ui.theme.Yellow


@Composable
fun MemberOvalItem(member : Member, onClick : () -> Unit){
    Card (modifier = Modifier
        .background(color = Color.Transparent)
        .clickable { onClick() }
    ){
        Column(
            modifier = Modifier.background(color = DarkBg2),
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            AsyncImage(
                modifier = Modifier
                    .height(96.dp)
                    .width(170.dp)
                    .clip(RoundedCornerShape(8.dp)),
                model = ImageRequest.Builder(LocalContext.current)
                    .data("https://img.cugate.com/?o=member&i=${member.id}&s=300")
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(id=R.drawable.some_image),
                error = painterResource(id=R.drawable.some_image),
                contentDescription = "Video thumbnail",
                contentScale = ContentScale.Crop
            )
            Text(
                text = member.title,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Start,
                maxLines = 2,
                modifier = Modifier
                    .width(170.dp)
                    .padding(5.dp, 5.dp)
            )
        }

    }
}


@Composable
fun VideoOvalItem(video : Video, onClick : () -> Unit){
    Card (modifier = Modifier
        .background(color = Color.Transparent)
        .clickable { onClick() }
    ){
        Column(
            modifier = Modifier.background(color = DarkBg2),
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            AsyncImage(
                modifier = Modifier
                    .height(96.dp)
                    .width(170.dp)
                    .clip(RoundedCornerShape(8.dp)),
                model = ImageRequest.Builder(LocalContext.current)
                    .data("https://img.youtube.com/vi/${video.youtube_id}/maxresdefault.jpg")
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(id=R.drawable.some_image),
                error = painterResource(id=R.drawable.some_image),
                contentDescription = "Video thumbnail",
                contentScale = ContentScale.Crop
            )
            Text(
                text = video.title,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Start,
                maxLines = 2,
                modifier = Modifier
                    .width(170.dp)
                    .padding(5.dp)
            )
        }

    }
}

@Composable
fun MemberHorizontalItem(member : Member, onClick : () -> Unit){
    Card (modifier = Modifier
        .background(color = Color.Transparent)
        .fillMaxWidth()
        .height(60.dp)
        .clickable { onClick() }
    ){
        Row(
            modifier = Modifier
                .background(color = DarkBg2)
                .fillMaxWidth()
                .padding(5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        )
        {
            Row(
                modifier = Modifier.height(60.dp),
                verticalAlignment = Alignment.CenterVertically
            ){
                AsyncImage(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .border(1.dp, Yellow, CircleShape),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data("https://img.cugate.com/?o=member&i=${member.id}&s=300")
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(id=R.drawable.some_image),
                    error = painterResource(id=R.drawable.some_image),
                    contentDescription = "Video thumbnail",
                    contentScale = ContentScale.Crop
                )
                Text(
                    text = member.title,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .padding(20.dp, 0.dp)
                )
            }

            Image(painter = painterResource(R.drawable.leftarrow)  , contentDescription = "show details")
        }

    }
}



@Composable
fun MemberVerticalItem(member : Member, onClick : () -> Unit){
    Card (modifier = Modifier
        .background(color = Color.Transparent)
        .width(107.dp)
        .clickable { onClick() }
    ){
        Column(
            modifier = Modifier
                .background(color = DarkBg2)
                .fillMaxWidth()
                .padding(5.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            AsyncImage(
                modifier = Modifier
                    .size(76.dp)
                    .clip(CircleShape)
                    .border(1.dp, Yellow, CircleShape),
                model = ImageRequest.Builder(LocalContext.current)
                    .data("https://img.cugate.com/?o=member&i=${member.id}&s=300")
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(id=R.drawable.some_image),
                error = painterResource(id=R.drawable.some_image),
                contentDescription = "Video thumbnail",
                contentScale = ContentScale.Crop
            )
            Text(
                text = member.title,
                fontSize = 14.sp,
                fontWeight = FontWeight.W600,
                color = Color.White,
                maxLines = 2,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(5.dp)
            )
        }

    }
}


@Composable
fun VerticalTrackItem(track : Track, onClick : () -> Unit, onMove : () -> Unit, onFavoriteClick : () -> Unit){
    var expanded = remember { mutableStateOf(false) }

    Box (modifier = Modifier
        .fillMaxWidth()
        .background(DarkBg2)
        .clickable { onClick() },
    ){
        Row(modifier = Modifier.fillMaxWidth()
            .padding(10.dp)
            .height(62.dp)
            .background(color = Color.Transparent),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                painter = painterResource(id=R.drawable.trackicon),
                contentDescription = "Image",
                contentScale = ContentScale.Crop,
            )
            Column( modifier = Modifier.padding(10.dp, 5.dp)
                .weight(1f)
                .align(Alignment.CenterVertically)

            ) {
                Text(
                    text = track.composers
                        .map { it.member_title}
                        .joinToString(","),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.W600,
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = track.title,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.W400,
                    color = Color.White,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = track.performers
                        .map { it.member_title}
                        .joinToString(","),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.W400,
                    fontStyle = FontStyle.Italic,
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
            Box {
                Image(
                    modifier = Modifier
                        .clickable {
                            expanded.value = true
                        },
                    painter = painterResource(id = R.drawable.dotsblue),
                    contentDescription = "menu"
                )

                DropdownMenu(
                    modifier = Modifier.background(GrayBlueLight),
                    expanded = expanded.value,
                    onDismissRequest = { expanded.value = false },
//                    offset = DpOffset(x = (-100).dp, y = 0.dp) // optional, tweak if needed
                ) {
                    DropdownMenuItem(
                        text = {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center,
                                text = "Go to Track",
                                color = DarkBg2
                            )
                        },
                        onClick = {
                            onMove()
                            expanded.value=false
                        }
                    )
                    DropdownMenuItem(
                        text = {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center,
                                text = "Go to Track",
                                color = DarkBg2
                            )
                        },
                        onClick = {
                            onFavoriteClick()
                            expanded.value=false
                        }
                    )
                }
            }

        }
    }
}


@Composable
fun VideoTrackItem(track : VideoTrack, onClick : () -> Unit, onMove : () -> Unit, onFavoriteClick : () -> Unit){

    var expanded = remember { mutableStateOf(false) }

    Box (modifier = Modifier
        .fillMaxWidth()
        .background(DarkBg2),
        contentAlignment = Alignment.CenterEnd
    ){
        Row(modifier = Modifier.fillMaxWidth()
            .height(62.dp)
            .background(color = Color.Transparent),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                modifier = Modifier.clickable { onClick() },
                painter = painterResource(id=R.drawable.trackicon),
                contentDescription = "Image",
                contentScale = ContentScale.Crop,
            )
            Column( modifier = Modifier.padding(10.dp, 5.dp).clickable { onClick() }
                .weight(1f)
                .align(Alignment.CenterVertically)

            ) {
                Row(modifier = Modifier) {

                    Text(
                        modifier = Modifier.padding(end = 8.dp),
                        text = formatSecondsToTime(track.starts_at),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.W600,
                        color = GrayBlue,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )

                    Text(
                        text = track.track.composers
                            .map { it.member_title}
                            .joinToString(","),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.W600,
                        color = Color.White,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }

                Text(
                    text = track.track.title,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.W400,
                    color = Color.White,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = track.track.performers
                        .map { it.member_title}
                        .joinToString(","),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.W400,
                    fontStyle = FontStyle.Italic,
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
            Box {
                Image(
                    modifier = Modifier
                        .clickable {
                            expanded.value = true
                        },
                    painter = painterResource(id = R.drawable.dotsblue),
                    contentDescription = "menu"
                )

                DropdownMenu(
                    modifier = Modifier.background(GrayBlueLight),
                    expanded = expanded.value,
                    onDismissRequest = { expanded.value = false },
//                    offset = DpOffset(x = (-100).dp, y = 0.dp) // optional, tweak if needed
                ) {
                    DropdownMenuItem(
                        text = {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center,
                                text = "Go to Track",
                                color = DarkBg2
                            )
                        },
                        onClick = {
                            expanded.value = false
                            onMove()
                        }
                    )
                    DropdownMenuItem(
                        text = {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center,
                                text = "Add to favorites",
                                color = DarkBg2
                            )
                        },
                        onClick = {
                            expanded.value = false
                            onFavoriteClick()
                        }
                    )
                }
            }


        }
    }
}


@Composable
fun HorizontalTrackItem(track : Track, onClick : () -> Unit){


    Surface() {
        Column(
            modifier = Modifier.padding(5.dp)
                .clickable { onClick() }
        ) {
            Image(
                painter = painterResource(id=R.drawable.some_image),
                contentDescription = "Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.width(90.dp)
                    .height(70.dp)
            )
            Text(
                modifier = Modifier.width(90.dp),
                text = track.title,
                fontSize = 15.sp,
                fontWeight = FontWeight.Normal  ,
                color = Color.Cyan,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                modifier = Modifier.width(90.dp),
                text = (track.performers + track.composers)
                    .map { it.member_title}
                    .joinToString(","),
                fontSize = 12.sp,
                fontWeight = FontWeight.Light,
                color = Color.Green,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}


@Composable
fun HorizontalVideoItem(video : Video, onClick: () -> Unit){
    Surface() {
        Column(
            modifier = Modifier.padding(5.dp)
                .clickable { onClick() }
        ) {
            AsyncImage(
                modifier = Modifier
                    .width(90.dp)
                    .height(90.dp),
                model = ImageRequest.Builder(LocalContext.current)
                    .data("https://img.cugate.com/?o=CUTV_VIDEO&i=${video.id}&s=300")
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(id=R.drawable.some_image),
                error = painterResource(id=R.drawable.some_image),
                contentDescription = "Video thumbnail",
                contentScale = ContentScale.Crop
            )
            Text(
                modifier = Modifier.width(90.dp),
                text = video.title,
                fontSize = 15.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Cyan,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )

        }
    }
}




//@Preview
//@Composable
//fun memberPreview(){
//    MemberHorizontalItem(Member(9, "dadsaaas sdasda xcaccs", 3), {})
//}


//@Preview
//@Composable
//fun memberPreview(){
//    VideoOvalItem(Video(9, "dadsaaas", "Antonio Vivaldi", "cdscsdcsdcw", 4532, "", 4 ,3 ,2), {})
//}


//@Preview
//@Composable
//fun memberPreview(){
//    VideoTrackItem(VideoTrack(Track(3, "johan sd sebastian bach", "fgjf", listOf(Member_title("dcfcn"), Member_title("dcfcn jhbuhdsc cdsc sxcsdc dscsdcvsvd sdcsdcsdcv sdvcsdvcsdv dvsdvsd")), listOf(Member_title("dcfcn dfhsbudcys dchasudca dcahcb dhab ahdba"), Member_title("dcfcn")), 6), 1, 0, 154 ), {}, {}, {})
//}


@Preview
@Composable
fun memberPreview(){
    VerticalTrackItem(Track(3, "johan sd sebastian bach", "fgjf", listOf(Member_title("dcfcn"), Member_title("dcfcn jhbuhdsc cdsc sxcsdc dscsdcvsvd sdcsdcsdcv sdvcsdvcsdv dvsdvsd")), listOf(Member_title("dcfcn dfhsbudcys dchasudca dcahcb dhab ahdba"), Member_title("dcfcn")), 6),  {}, {}, {})
}

//
//@Preview
//@Composable
//fun memberPreview(){
//    VerticalVideoItem(Video(9, "dadsaaas", "Antonio Vivaldi", "cdscsdcsdcw", 4532, "", 4 ,3 ,2), {})
//}
