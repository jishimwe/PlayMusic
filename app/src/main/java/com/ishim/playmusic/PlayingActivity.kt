package com.ishim.playmusic

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.ishim.playmusic.MusicDB.Companion.getTracks
import com.ishim.playmusic.compose_layout.MyTheme
import com.ishim.playmusic.compose_layout.PlayMusicViewModel
import com.ishim.playmusic.compose_layout.PlayingView
import com.ishim.playmusic.compose_layout.SONG_ID
import kotlinx.coroutines.launch

private val TAG = "PlayingActivity"

class PlayingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val songId = intent?.extras?.getLong(SONG_ID)
        val song = getTracks(this, songdId = songId)[0]
        val currentlyPlaying = CurrentlyPlaying()
        val viewModel: PlayMusicViewModel by viewModels()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    setContent {
                        MyTheme() {
                            Log.d(TAG, "$currentlyPlaying -- $song.id --> $song.name")
                            PlayingView(song = song, currentlyPlaying, viewModel)
                        }
                    }
                }
            }
        }
    }
}
// Remote changes
