/*
*   File containing dummy data to to use when running a composable @Preview
*/

package com.ishim.playmusic.compose_layout

import android.net.Uri
import com.ishim.playmusic.Album
import com.ishim.playmusic.Artist
import com.ishim.playmusic.MusicDB.Companion.getAlbums
import com.ishim.playmusic.MusicDB.Companion.getTracks
import com.ishim.playmusic.Playlist
import com.ishim.playmusic.Song

val DUMMY_SONG = Song(
    1,
    Uri.EMPTY,
    "Scream?",
    "Dreamcatcher?",
    "1st Album?",
    "Dummy",
    180000,
    "Dummy data"
)

val DUMMY_SONG_LIST = listOf(
    Song(
        2,
        Uri.EMPTY,
        "Scream?",
        "Dreamcatcher?",
        "1st Album?",
        "Dummy",
        180000,
        "Dummy data"),
    Song(
        3,
        Uri.EMPTY,
        "Scream2?",
        "Dreamcatcher?",
        "2st Album?",
        "Dummy",
        180000,
        "Dummy data"),
    Song(
        4,
        Uri.EMPTY,
        "Scream3?",
        "Dreamcatcher?",
        "3st Album?",
        "Dummy",
        180000,
        "Dummy data"),
    Song(
        0,
        Uri.EMPTY,
        "Scream4?",
        "Dreamcatcher?",
        "4st Album?",
        "Dummy",
        180000,
        "Dummy data"),
    Song(
        11,
        Uri.EMPTY,
        "Scream5?",
        "Dreamcatcher?",
        "5st Album?",
        "Dummy",
        180000,
        "Dummy data"),
    Song(
        45,
        Uri.EMPTY,
        "Scream6?",
        "Dreamcatcher?",
        "6st Album?",
        "Dummy",
        180000,
        "Dummy data"),
    Song(
        485,
        Uri.EMPTY,
        "Scream7?",
        "Dreamcatcher?",
        "7st Album?",
        "Dummy",
        180000,
        "Dummy data"),
    Song(
        886496541,
        Uri.EMPTY,
        "Scream8?",
        "Dreamcatcher?",
        "8st Album?",
        "Dummy",
        180000,
        "Dummy data"),
    Song(
        1478532,
        Uri.EMPTY,
        "Scream9?",
        "Dreamcatcher?",
        "9st Album?",
        "Dummy",
        180000,
        "Dummy data"),
    Song(
        1254,
        Uri.EMPTY,
        "Scream10?",
        "Dreamcatcher?",
        "10st Album?",
        "Dummy",
        180000,
        "Dummy data"),
    Song(
        123456789,
        Uri.EMPTY,
        "Scream11?",
        "Dreamcatcher?",
        "11st Album?",
        "Dummy",
        180000,
        "Dummy data")
)

val DUMMY_PLAYLISTS = listOf(
    Playlist(
        Uri.EMPTY,
        "Play list 1",
        ::getTracks
    ),
    Playlist(
        Uri.EMPTY,
        "Play list 2",
        ::getTracks
    ),
    Playlist(
        Uri.EMPTY,
        "Play list 3",
        ::getTracks
    )
)

val DUMMY_ALBUM_LIST = listOf(
    Album(
        Uri.EMPTY,
        "++",
        "Loona",
        ::getTracks
    ),
    Album(
        Uri.EMPTY,
        "Ice",
        "Hyolin",
        ::getTracks
    ),
    Album(
        Uri.EMPTY,
        "Would you like?",
        "WJSN",
        ::getTracks
    ),
    Album(
        Uri.EMPTY,
        "Dystopia : The Tree of Language",
        "Dreamcatcher",
        ::getTracks
    )
)

val DUMMY_ARTIST_LIST = listOf(
    Artist(
        Uri.EMPTY,
        "Loona",
        ::getAlbums
    ),
    Artist(
        Uri.EMPTY,
        "Hyolin",
        ::getAlbums
    ),
    Artist(
        Uri.EMPTY,
        "Dreamcatcher",
        ::getAlbums
    )
)

val DUMMY_ARTIST = Artist(
    Uri.EMPTY,
    "Dreamcatcher",
    ::getAlbums
)