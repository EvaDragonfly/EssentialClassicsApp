package com.memoittech.cuviewtv.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView


@Composable
fun PlayerComponent(
    youtube_id : String,
    start_at : Float,
    onReady: (YouTubePlayer) -> Unit
){
    AndroidView(
        modifier = Modifier.padding(10.dp, 0.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp)),
        factory = { context ->

            val view = YouTubePlayerView(context)
            view.enableAutomaticInitialization = false

            view.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    youTubePlayer.loadVideo(youtube_id, start_at) // load but don't play
                    onReady(youTubePlayer)
                }
            })

            view
        })
}