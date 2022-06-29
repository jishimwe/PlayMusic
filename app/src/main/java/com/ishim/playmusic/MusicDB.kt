package com.ishim.playmusic

import android.app.Application

import android.content.Context
import android.database.AbstractCursor
import android.database.Cursor
import android.provider.MediaStore
import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView

class MusicDB {
    companion object {
        fun getTracks(applicationContext: Context, trackCardView: CardView) {

            val projection = arrayOf(
                MediaStore.Audio.Media.TRACK,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.ARTIST
            )
            val selection = "${MediaStore.Audio.Media.TRACK} = *"
            val selectionArgs = null
            val sortOrder =
                "ORDER BY ${MediaStore.Audio.Media.ARTIST}, ${MediaStore.Audio.Media.ALBUM}, ${MediaStore.Audio.Media.TRACK}"

            applicationContext.contentResolver.query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                projection,
                selection,
                selectionArgs,
                sortOrder
            )?.use { cursor ->
                val track = cursor.getColumnIndex(MediaStore.Audio.Media.TRACK)
                val album = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM)
                val artist = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)
                while (cursor.moveToNext()) {
                    val trackName = cursor.getString(track)
                    val albumName = cursor.getString(album)
                    val artistName = cursor.getString(artist)
                    println("Track - $trackName \t Artist - $artistName \t Album - $albumName")
                }
            }
        }

        fun bindTrackCard(trackCardView: CardView, context: Context, title: String, album: String, artist: String) {

        }
    }
}