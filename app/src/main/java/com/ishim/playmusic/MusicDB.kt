package com.ishim.playmusic

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.ContentUris
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import android.provider.MediaStore
import android.util.Log
import java.time.format.DateTimeFormatter


data class Song(
    val id: Long,
    val uri: Uri,
    val name: String,
    val artist: String,
    val album: String,
    val displayName: String,
    val duration: Long,
    val data: String
)

data class Album(
    val id: Long,
    val uri: Uri,
    val name: String,
    val artist: String,
    val songs: ((applicationContext: Context, albumFilter: String) -> List<Song>)
)

data class Artist(
    val uri: Uri,
    val name: String,
    val albums: ((applicationContext: Context, artistFilter: String) -> List<Album>)
)

data class Playlist(
    val uri: Uri,
    val name: String,
    val songs: ((applicationContext: Context, playlistFilter: String) -> List<Song>)
)

private const val TAG = "MusicDB"
val timeFormat = DateTimeFormatter.ofPattern("mm:ss")

//class MusicDB (songs: List<Song>, albums: List<Album>, artists: List<Artist>, playlists: List<Playlist>){
class MusicDB(context: Context){

    var songs = getTracks(context)
    var albums = getAlbums(context)
    var artists = getArtists(context)
    var playlists = getPlaylists(context)

    companion object {
        fun getTracks(
            applicationContext: Context,
            albumFilter: String? = null,
            artistFilter: String? = null,
            playlistFilter: String? = null,
            songdId: Long? = null
        ): MutableList<Song> {

            val trackList = mutableListOf<Song>()
            val projection = arrayOf(
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.DATA,
            )

            val collection =
                MediaStore.Audio.Media.getContentUri(MediaStore.VOLUME_EXTERNAL)

            var selection = "(${MediaStore.Audio.Media.IS_MUSIC} != 0)"
            var selectionArgs = arrayOf<String>()
            if (albumFilter != null) {
                selection += " AND (${MediaStore.Audio.Media.ALBUM} = ?)"
                selectionArgs += "$albumFilter"
            }
            if (artistFilter != null) {
                selection += " AND (${MediaStore.Audio.Media.ARTIST} = ?)"
                selectionArgs += "$artistFilter"
            }
            if (songdId != null) {
                selection += "AND (${MediaStore.Audio.Media._ID} = ?)"
                selectionArgs += "$songdId"
            }
            // TODO: Add playlist filter

            val sortOrder =
                "${MediaStore.Audio.Media.ARTIST}, ${MediaStore.Audio.Media.ALBUM}, ${MediaStore.Audio.Media.TRACK}"
//            val sortOrder = MediaStore.Audio.Media.ARTIST

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
                Log.d(
                    TAG,
                    "Getting tracks CURSOR $idColumn $title $album $artist $name $duration $data"
                )
                Log.d(
                    TAG,
                    "$cursor WTH is happening [Media Manage: ${
                        MediaStore.canManageMedia(applicationContext)
                    }]"
                )
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
                    val song = Song(
                        id,
                        contentUri,
                        trackName,
                        artistName,
                        albumName,
                        displayName,
                        durationTime,
                        dataName
                    )
                    trackList.add(song)
                }
            }
            if (trackList.isEmpty())
                trackList += Song(
                    1001,
                    Uri.EMPTY,
                    "Title",
                    "Artist?",
                    "Album?",
                    "Dummy",
                    180000,
                    "Dummy data"
                )
            return trackList
        }

        fun getAlbums(
            applicationContext: Context,
            artistFilter: String? = null
        ): MutableList<Album> {
            val albumList = mutableListOf<Album>()
            val projection = arrayOf(
                MediaStore.Audio.Albums.ALBUM_ID,
                MediaStore.Audio.Albums.ALBUM,
                MediaStore.Audio.Albums.ARTIST
            )

            val collection =
                MediaStore.Audio.Albums.getContentUri(MediaStore.VOLUME_EXTERNAL)

            var selection: String? = null
            var selectionArgs = arrayOf<String>()
            val sortOrder =
                "${MediaStore.Audio.Albums.ARTIST}, ${MediaStore.Audio.Albums.DEFAULT_SORT_ORDER}"

            if (artistFilter != null) {
                selection = "${MediaStore.Audio.Albums.ARTIST} = ?"
                selectionArgs += artistFilter
            }

            Log.d(TAG, "Getting albums")

            applicationContext.contentResolver.query(
                collection,
                projection,
                selection,
                selectionArgs,
                sortOrder
            )?.use { cursor ->
                val idCol = cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ID)
                val albumCol = cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM)
                val artistCol = cursor.getColumnIndex(MediaStore.Audio.Albums.ARTIST)

                Log.d(TAG, "Getting albums CURSOR $idCol $albumCol $artistCol")

                while (cursor.moveToNext()) {
                    val id = cursor.getLong(idCol)
                    val albumName = cursor.getString(albumCol)
                    val artist = cursor.getString(artistCol)
                    val albumUri = ContentUris.withAppendedId(collection, id)

                    Log.d(TAG, "Track - $id \t Artist - $albumName \t Album - $artist")

                    val album = Album(
                        id = id,
                        uri = albumUri,
                        name = albumName,
                        artist = artist,
                        songs = ::getTracks
                    )
                    albumList += album
                }
            }
            return albumList
        }

        fun getArtists(applicationContext: Context): MutableList<Artist> {
            val artistList = mutableListOf<Artist>()

            val projection = arrayOf(
                MediaStore.Audio.Artists._ID,
                MediaStore.Audio.Artists.ARTIST,
            )

            val collection =
                MediaStore.Audio.Artists.getContentUri(MediaStore.VOLUME_EXTERNAL)

            val selection: String? = null
            val selectionArgs = arrayOf<String>()
            val sortOrder = MediaStore.Audio.Artists.DEFAULT_SORT_ORDER

            Log.d(TAG, "Getting artists")

            applicationContext.contentResolver.query(
                collection,
                projection,
                selection,
                selectionArgs,
                sortOrder
            )?.use { cursor ->
                val idCol = cursor.getColumnIndex(MediaStore.Audio.Artists._ID)
                val artistCol = cursor.getColumnIndex(MediaStore.Audio.Artists.ARTIST)

                Log.d(TAG, "Getting albums CURSOR $idCol $artistCol")

                while (cursor.moveToNext()) {
                    val id = cursor.getLong(idCol)
                    val artistName = cursor.getString(artistCol)
                    val artistUri = ContentUris.withAppendedId(collection, id)

                    val artist =
                        Artist(
                            artistUri,
                            artistName,
                            ::getAlbums
                        )

                    artistList += artist
                }
            }
            return artistList
        }

        @SuppressLint("Range")
        fun getAlbumArt(context: Context, albumId: Long): Bitmap? {
            val albumArtUri = Uri.parse("content://media/external/audio/albumart")
            val contentUri = ContentUris.withAppendedId(albumArtUri, albumId)

            val projection = arrayOf(MediaStore.Audio.Albums._ID)
            val selection = "${MediaStore.Audio.Albums._ID} = ?"
            val selectionArgs = arrayOf(albumId.toString())

            context.contentResolver.query(contentUri, projection, selection, selectionArgs, null)?.use { cursor ->
                if (cursor.moveToFirst()) {
                    val id = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Albums._ID))
                    val alArt = ContentUris.withAppendedId(albumArtUri, id)
                    val contentResolver = context.contentResolver
                    val inputStream = contentResolver.openInputStream(alArt)
                    val bitmap: Bitmap
                    if (inputStream != null) {
                        bitmap = BitmapFactory.decodeStream(inputStream)
                        inputStream.close()
                        return bitmap
                    }
                }
            }

            return null
        }

        fun getPlaylists(applicationContext: Context) {
            // TODO: M3U files for playlists
        }
    }
}