package com.example.spotify.exoplayer.callbacks

import android.net.Uri
import android.os.Bundle
import android.os.ResultReceiver
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.PlaybackStateCompat
import com.example.spotify.exoplayer.FirebaseMusicSource
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector

class MusicPlaybackPreparer(
    private val firebaseMusicSource: FirebaseMusicSource,
    private val playerPrepared: (MediaMetadataCompat?) -> Unit
): MediaSessionConnector.PlaybackPreparer {

    override fun onCommand(p0: Player, p1: String, p2: Bundle?, p3: ResultReceiver?) = false

    override fun getSupportedPrepareActions(): Long =
        PlaybackStateCompat.ACTION_PREPARE_FROM_MEDIA_ID or PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID

    override fun onPrepare(p0: Boolean) = Unit

    override fun onPrepareFromMediaId(mediaId: String, p1: Boolean, p2: Bundle?) {
        firebaseMusicSource.whenReady {
            val itemToPlay = firebaseMusicSource.songs.find { mediaId == it.description.mediaId }
            playerPrepared(itemToPlay)

        }
    }

    override fun onPrepareFromSearch(p0: String, p1: Boolean, p2: Bundle?) = Unit

    override fun onPrepareFromUri(p0: Uri, p1: Boolean, p2: Bundle?) = Unit
}