/*
*   File containing dummy data to to use when running a composable @Preview
*/

package com.ishim.playmusic.compose_layout

import android.net.Uri
import com.ishim.playmusic.Album
import com.ishim.playmusic.Artist
import com.ishim.playmusic.Playlist
import com.ishim.playmusic.Song

val DUMMY_SONG = Song(
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
        Uri.EMPTY,
        "Scream?",
        "Dreamcatcher?",
        "1st Album?",
        "Dummy",
        180000,
        "Dummy data"),
    Song(
        Uri.EMPTY,
        "Scream2?",
        "Dreamcatcher?",
        "2st Album?",
        "Dummy",
        180000,
        "Dummy data"),
    Song(
        Uri.EMPTY,
        "Scream3?",
        "Dreamcatcher?",
        "3st Album?",
        "Dummy",
        180000,
        "Dummy data"),
    Song(
        Uri.EMPTY,
        "Scream4?",
        "Dreamcatcher?",
        "4st Album?",
        "Dummy",
        180000,
        "Dummy data"),
    Song(
        Uri.EMPTY,
        "Scream5?",
        "Dreamcatcher?",
        "5st Album?",
        "Dummy",
        180000,
        "Dummy data"),
    Song(
        Uri.EMPTY,
        "Scream6?",
        "Dreamcatcher?",
        "6st Album?",
        "Dummy",
        180000,
        "Dummy data"),
    Song(
        Uri.EMPTY,
        "Scream7?",
        "Dreamcatcher?",
        "7st Album?",
        "Dummy",
        180000,
        "Dummy data"),
    Song(
        Uri.EMPTY,
        "Scream8?",
        "Dreamcatcher?",
        "8st Album?",
        "Dummy",
        180000,
        "Dummy data"),
    Song(
        Uri.EMPTY,
        "Scream9?",
        "Dreamcatcher?",
        "9st Album?",
        "Dummy",
        180000,
        "Dummy data"),
    Song(
        Uri.EMPTY,
        "Scream10?",
        "Dreamcatcher?",
        "10st Album?",
        "Dummy",
        180000,
        "Dummy data"),
    Song(
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
        DUMMY_SONG_LIST
    ),
    Playlist(
        Uri.EMPTY,
        "Play list 2",
        DUMMY_SONG_LIST
    ),
    Playlist(
        Uri.EMPTY,
        "Play list 3",
        DUMMY_SONG_LIST
    )
)

val DUMMY_ALBUM_LIST = listOf(
    Album(
        Uri.EMPTY,
        "++",
        "Loona",
        DUMMY_SONG_LIST
    ),
    Album(
        Uri.EMPTY,
        "Ice",
        "Hyolin",
        DUMMY_SONG_LIST
    ),
    Album(
        Uri.EMPTY,
        "Would you like?",
        "WJSN",
        DUMMY_SONG_LIST
    ),
    Album(
        Uri.EMPTY,
        "Dystopia : The Tree of Language",
        "Dreamcatcher",
        DUMMY_SONG_LIST
    )
)

val DUMMY_ARTIST_LIST = listOf(
    Artist(
        Uri.EMPTY,
        "Loona",
        DUMMY_ALBUM_LIST
    ),
    Artist(
        Uri.EMPTY,
        "Hyolin",
        DUMMY_ALBUM_LIST
    ),
    Artist(
        Uri.EMPTY,
        "Dreamcatcher",
        DUMMY_ALBUM_LIST
    )
)

val DUMMY_ARTIST = Artist(
    Uri.EMPTY,
    "Dreamcatcher",
    DUMMY_ALBUM_LIST
)