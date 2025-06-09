package com.memoittech.cuviewtv.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.DropdownMenu
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.memoittech.cuviewtv.R
import com.memoittech.cuviewtv.model.Member
import com.memoittech.cuviewtv.model.Track
import com.memoittech.cuviewtv.model.VideoTrack
import com.memoittech.cuviewtv.ui.theme.DarkBg2
import com.memoittech.cuviewtv.ui.theme.GrayBlue
import com.memoittech.cuviewtv.ui.theme.GrayBlueLight
import com.memoittech.cuviewtv.ui.theme.PlaceHolderColor
import com.memoittech.cuviewtv.ui.theme.Violet
import com.memoittech.cuviewtv.ui.theme.Yellow
import com.memoittech.cuviewtv.viewModel.TracksViewModel


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
                placeholder = painterResource(id=R.drawable.artist),
                error = painterResource(id=R.drawable.artist),
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
fun VideoOvalItem(id : Int, youtube_id:String, title: String, has_thumbnail : Boolean, onClick: () -> Unit){
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
                    .data(if (has_thumbnail) "https://img.cugate.com/?o=eclass_video&i=${id}&s=300" else "https://img.youtube.com/vi/${youtube_id}/maxresdefault.jpg")
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(id=R.drawable.video),
                error = painterResource(id=R.drawable.video),
                contentDescription = "Video thumbnail",
                contentScale = ContentScale.Crop
            )
            Text(
                text = title,
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
                    placeholder = painterResource(id=R.drawable.composer),
                    error = painterResource(id=R.drawable.composer),
                    contentDescription = "member thumbnail",
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
                placeholder = painterResource(id=R.drawable.composer),
                error = painterResource(id=R.drawable.composer),
                contentDescription = "member thumbnail",
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
fun VerticalTrackItem(track : Track, active_track_id : Int, onClick : () -> Unit, onMove : () -> Unit){

    var expanded = remember { mutableStateOf(false) }

    val backgroundColor = if (track.id == active_track_id) Violet else DarkBg2

    val tracksViewModel : TracksViewModel = viewModel()

    var trackItem by remember { mutableStateOf(track) }

    fun onFavoriteClick(item : Track){
        if(trackItem.is_favorite){
            tracksViewModel.addFavoriteTrack(item.id, true)
        } else {
            tracksViewModel.addFavoriteTrack(item.id, false)
        }
        trackItem = trackItem.copy(is_favorite = !trackItem.is_favorite)
    }

    Box (modifier = Modifier
        .fillMaxWidth()
        .background(backgroundColor)
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
                    text = trackItem.composers
                        .map { it.member_title}
                        .joinToString(","),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.W600,
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = trackItem.title,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.W400,
                    color = Color.White,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = trackItem.performers
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
                                text = if (trackItem.is_favorite) "Delete from favorites" else "Add to favorites",
                                color = DarkBg2
                            )
                        },
                        onClick = {
                            onFavoriteClick(trackItem)
                            expanded.value=false
                        }
                    )
                }
            }

        }
    }
}


@Composable
fun VideoTrackItem(track : VideoTrack, onClick : () -> Unit, onMove : () -> Unit){

    var expanded = remember { mutableStateOf(false) }

    var trackItem by remember { mutableStateOf(track.track) }

    val tracksViewModel : TracksViewModel = viewModel()

    fun onFavoriteClick(item : Track){
        if(trackItem.is_favorite){
            tracksViewModel.addFavoriteTrack(item.id, true)
        } else {
            tracksViewModel.addFavoriteTrack(item.id, false)
        }
        trackItem = trackItem.copy(is_favorite = !trackItem.is_favorite)
    }

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
                        text = trackItem.composers
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
                    text = trackItem.title,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.W400,
                    color = Color.White,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = trackItem.performers
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
                                text = if (trackItem.is_favorite) "Delete from favorites" else "Add to favorites",
                                color = DarkBg2
                            )
                        },
                        onClick = {
                            expanded.value = false
                            onFavoriteClick(trackItem)
                        }
                    )
                }
            }


        }
    }
}


@Composable
fun Separator(){
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
}


@Composable
fun FavoritesItem(icon : Int, name : String, number : Int, onClick: () -> Unit){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(DarkBg2)
            .clickable { onClick() }
            .drawBehind {
                val strokeWidth = 1.dp.toPx()
                val y = size.height - strokeWidth / 2
                drawLine(
                    color = Violet,
                    start = Offset(0f, y),
                    end = Offset(size.width, y),
                    strokeWidth = strokeWidth
                )
            }

    ){
        Row( modifier = Modifier.fillMaxWidth()
            .padding(20.dp, 15.dp)){
            Image(
                painter = painterResource(icon),
                contentDescription = "icon"
            )
            Text(
                text = name,
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.W500,
                modifier = Modifier.padding(start = 20.dp).weight(1f)
            )
            Text(
                text = number.toString(),
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.W500,
                modifier = Modifier.padding(start = 20.dp)
            )
        }
    }

}

@Composable
fun SearchComponent(text:String, onTextChange: (String)->Unit,){
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
            onValueChange = onTextChange,
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
                .height(56.dp)
                .align(Alignment.Center)
                .clip(RoundedCornerShape(6.dp))
                .background(Color(0xFF2C3C5C)),
            placeholder = {
                Text(
                    text = "Type min 3 characters to search...",
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
                    IconButton(onClick = { onTextChange("") }) {
                        Image(
                            painter = painterResource(R.drawable.crossicon),
                            contentDescription = "Cancel"
                        )
                    }
                }
            }
        )

    }
}


//@Preview
//@Composable
//fun favoritesPrev(){
//    FavoritesItem(R.drawable.artistsiconwhite, "Artists", 100, {})
//}

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
//    VideoTrackItem(VideoTrack(Track(3, "johan sd sebastian bach", "fgjf",  listOf(Member_Wrapper(3, "dcfcn"), Member_Wrapper(5,"dcfcn jhbuhdsc cdsc sxcsdc dscsdcvsvd sdcsdcsdcv sdvcsdvcsdv dvsdvsd")), listOf(Member_Wrapper(6,"dcfcn dfhsbudcys dchasudca dcahcb dhab ahdba"), Member_Wrapper(7, "dcfcn")), 6), 1, 0, 154 ), {}, {}, {})
//}


//@Preview
//@Composable
//fun memberPreview(){
//    VerticalTrackItem(Track(3, "johan sd sebastian bach", "fgjf", listOf(Member_Wrapper(3, "dcfcn"), Member_Wrapper(5,"dcfcn jhbuhdsc cdsc sxcsdc dscsdcvsvd sdcsdcsdcv sdvcsdvcsdv dvsdvsd")), listOf(Member_Wrapper(6,"dcfcn dfhsbudcys dchasudca dcahcb dhab ahdba"), Member_Wrapper(7, "dcfcn")), 6, true), 3, {}, {}, {})
//}

//
//@Preview
//@Composable
//fun memberPreview(){
//    VerticalVideoItem(Video(9, "dadsaaas", "Antonio Vivaldi", "cdscsdcsdcw", 4532, "", 4 ,3 ,2), {})
//}
