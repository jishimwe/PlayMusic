package com.ishim.playmusic.compose_layout

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraph
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navDeepLink

object NavDestinations {
    const val SONG_LIST_VIEW = "songs_view"
    const val ALBUM_LIST_VIEW = "albums_view"
    const val ARTIST_LIST_VIEW = "artist_view"
    const val PLAYLIST_LIST_VIEW = "playlist_view"
    const val CURRENTLY_PLAYING_VIEW = "currently_playing_view"
}

const val NAV_SONG_ID = "song_id"


@Composable
fun PlayMusicNavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavDestinations.SONG_LIST_VIEW
    ) {
        composable(
            route = NavDestinations.SONG_LIST_VIEW,
            deepLinks = listOf(
                navDeepLink {
                    uriPattern = "${NavDestinations.SONG_LIST_VIEW}?$NAV_SONG_ID={$NAV_SONG_ID}" })
        ) {

        }
    }
}