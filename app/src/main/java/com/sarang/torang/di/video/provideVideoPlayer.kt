package com.sarang.torang.di.video

import androidx.compose.runtime.Composable


fun provideVideoPlayer(): @Composable (String, Boolean, () -> Unit) -> Unit {
    return { url, isPlaying, onVideoClick ->
        VideoPlayerScreen(
            videoUrl = url,
            isPlaying = isPlaying,
            onClick = onVideoClick,
            onPlay = {})
    }
}