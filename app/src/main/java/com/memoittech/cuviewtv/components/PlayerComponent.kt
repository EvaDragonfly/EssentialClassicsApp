package com.memoittech.cuviewtv.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.YouTubePlayerTracker
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView


@Composable
fun PlayerComponent(
    youtube_id : String,
    starts_at : Float,
    youTubePlayerTracker: YouTubePlayerTracker,
    onReady: (YouTubePlayer) -> Unit
){

    val context = LocalContext.current
    val view = remember { YouTubePlayerView(context) }

    AndroidView(
        modifier = Modifier.padding(10.dp, 0.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp)),
        factory = {

//            val view = YouTubePlayerView(context)
            view.enableAutomaticInitialization = false

            view.addYouTubePlayerListener(youTubePlayerTracker)

            view.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    youTubePlayer.loadVideo(youtube_id, starts_at) // load but don't play
                    onReady(youTubePlayer)
                }
            })

            view
        })
    DisposableEffect(Unit) {
        onDispose {
            view.release()  // 👈 This stops the video & releases player
        }
    }
}