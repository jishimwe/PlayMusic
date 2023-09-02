package com.ishim.playmusic

import android.content.Context
import android.media.MediaPlayer
import android.util.Log

private const val TAG = "MusicCommands"

class MusicCommands {

    companion object {
        fun playSong(context: Context, song: Song) {
            MediaPlayer().apply {
                setDataSource(context, song.uri)
                prepare()
                start()
            }
        }

        fun pauseSong(context: Context, song: Song, play: Boolean) {

        }

        fun stopSong(context: Context, song: Song) {
            MediaPlayer().apply {
                setDataSource(context, song.uri)
                prepare()
                stop()
            }
        }


        fun nextSong() {}


        fun previousSong() {}
    }
}

class CurrentlyPlaying private constructor() {

    private var currentlyPlaying: MediaPlayer = MediaPlayer()
    private lateinit var playlist: List<Song>
    private var currentSongID: Long = -1

    companion object {
        private var instance: CurrentlyPlaying? = null

        operator fun invoke(): CurrentlyPlaying {
            if (instance == null)
                instance = CurrentlyPlaying()

            return instance!!
        }

        fun getInstance(): CurrentlyPlaying {
            if (instance == null)
                instance = CurrentlyPlaying()

            return instance!!
        }

        fun setPlaylist(list: List<Song>) {
            instance?.playlist = list;
        }

        fun playing(song: Song, playlist: List<Song>, context: Context) {
            instance?.playlist = playlist
            instance?.playSong(song, context)
        }
    }

    fun getSongID(): Long {
        return currentSongID;
    }

    fun playSong(song: Song, context: Context) {
        Log.d(TAG, "PlaySong $currentSongID -- $song.id --> $song.name")
        // Change song playing
        if (currentSongID != song.id) {
            if (currentlyPlaying.isPlaying)
                currentlyPlaying.pause()
            currentlyPlaying.stop()
            currentlyPlaying.reset()
        }
        // Resume song playing
        else {
//            currentlyPlaying.prepareAsync()
            currentlyPlaying.start()
            return
        }
        currentlyPlaying.apply {
            setDataSource(context, song.uri)
            prepare()
            start()
        }
        currentSongID = song.id
        Log.d(TAG, "PlaySong $currentSongID -- $song.id --> $song.name SONG PLAYED")
    }

    fun pauseSong(song: Song, context: Context) {
        currentlyPlaying.pause()
        Log.d(TAG, "PauseSong $currentSongID -- $song.id --> $song.name SONG PAUSED")
    }
}