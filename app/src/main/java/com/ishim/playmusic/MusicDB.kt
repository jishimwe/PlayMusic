package com.ishim.playmusic

import android.content.ContentUris

import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import kotlin.time.Duration

data class SongCardInfo(
    val uri: Uri,
    val name: String,
    val artist: String,
    val album: String,
    val displayName: String,
    val duration: Long,
    val data: String
)

private const val TAG = "MusicDB"

class MusicDB {

    companion object {
        fun getTracks(applicationContext: Context): MutableList<SongCardInfo> {

            val trackList = mutableListOf<SongCardInfo>()
            val projection = arrayOf(
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.DATA
            )

            val collection =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    MediaStore.Audio.Media.getContentUri(MediaStore.VOLUME_EXTERNAL)
                } else {
                    MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                }

            val selection = "${MediaStore.Audio.Media.IS_MUSIC} != 0"
            val selectionArgs = null
//            val sortOrder = "${MediaStore.Audio.Media.ARTIST}, ${MediaStore.Audio.Media.ALBUM}, ${MediaStore.Audio.Media.TRACK}"
            val sortOrder = MediaStore.Audio.Media.ARTIST

            Log.d(TAG, "Getting tracks")
            applicationContext.contentResolver.query(
                collection,
                projection,
                selection,
                selectionArgs,
                sortOrder
            )?.use { cursor ->
                val idColumn = cursor.getColumnIndex(MediaStore.Audio.Media._ID)
                val title = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)
                val album = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM)
                val artist = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)
                val name = cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME)
                val duration = cursor.getColumnIndex(MediaStore.Audio.Media.DURATION)
                val data = cursor.getColumnIndex(MediaStore.Audio.Media.DURATION)
                Log.d(TAG, "Getting tracks CURSOR $idColumn $title $album $artist $name $duration $data")
                Log.d(
                    TAG,
                    "$cursor WTH is happening [Media Manage: ${
                        MediaStore.canManageMedia(applicationContext)
                    }]"
                )
                /*                if (!cursor.isNull(track)) {
                                    Log.d(TAG, "Getting tracks CURSOR ${cursor.getString(track)} ${cursor.getString(album)} ${cursor.getString(artist)}")
                                }*/
                while (cursor.moveToNext()) {
                    val id = cursor.getLong(idColumn)
                    val trackName = cursor.getString(title)
                    val albumName = cursor.getString(album)
                    val artistName = cursor.getString(artist)
                    val contentUri: Uri = ContentUris.withAppendedId(collection, id)
                    val displayName = cursor.getString(name)
                    val durationTime = cursor.getLong(duration)
                    val dataName = cursor.getString(data)
                    Log.d(TAG, "Track - $trackName \t Artist - $artistName \t Album - $albumName")
                    val songCardInfo = SongCardInfo(
                        contentUri,
                        trackName,
                        artistName,
                        albumName,
                        displayName,
                        durationTime,
                        dataName
                    )
                    trackList.add(songCardInfo)
                }
            }
            if (trackList.isEmpty())
                trackList += SongCardInfo(Uri.EMPTY, "Scream?", "Dreamcatcher?", "1st Album?", "Dummy", 180000, "Dummy data")
            return trackList
        }
    }
}