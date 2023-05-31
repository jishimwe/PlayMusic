package com.ishim.playmusic

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.ishim.playmusic.MusicDB.Companion.getTracks
import com.ishim.playmusic.compose_layout.MyTheme
import com.ishim.playmusic.compose_layout.PlayingView
import com.ishim.playmusic.compose_layout.SONG_ID

class PlayingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val songId = intent?.extras?.getLong(SONG_ID)
        val song = getTracks(this, songdId = songId)[0]
        setContent {
            MyTheme() {
                PlayingView(song = song)
            }
        }
    }
}
// Remote changes
