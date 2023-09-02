package com.ishim.playmusic.compose_layout

import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ishim.playmusic.MusicDB


private const val TAG = "NavigationGraph"
object NavDestinations {
    const val SONG_LIST_VIEW = "tracks"
    const val ALBUM_LIST_VIEW = "albums"
    const val ARTIST_LIST_VIEW = "artists"
    const val PLAYLIST_LIST_VIEW = "playlists"
    const val CURRENTLY_PLAYING_VIEW = "currently_playing_view"
}

const val NAV_SONG_ID = "song_id"

@Composable
fun PlayMusicApp(musicDB: MusicDB, currentlyPlayingState: CurrentlyPlayingState) {
    MyTheme {
        Log.d(TAG, "Navigation is gonna be hell")

        val navController = rememberNavController()
        val currentBackStack by navController.currentBackStackEntryAsState()
        val currentDestination = currentBackStack?.destination

        val currentScreen = playMusicTabs.find { it.route == currentDestination?.route } ?: SongListView
        ScaffoldBarsGeneric(
            context = LocalContext.current,
            list = listOf(),
            musicDB = musicDB,
            songPlaying = currentlyPlayingState.song.value,
            allDestinations = playMusicTabs,
            currentScreen = currentScreen,
            navController = navController,
            onSelectedTab = { newScreen ->
                navController.navigateSingleTopTo(newScreen.route)
            },
            currentlyPlayingState = currentlyPlayingState
        )
    }
    Log.d(TAG, "Navigation is gonna be heaven")
}

@Composable
fun PlayMusicNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    currentlyPlayingState: CurrentlyPlayingState,
    musicDB: MusicDB
) {
    Log.d(TAG, "PlayMusicNavGraph -- What's happening")
    NavHost(
        navController = navController,
        startDestination = SongListView.route,
        modifier = modifier
    ) {
        Log.d(TAG, "PlayMusicNavGraph -- composable BEGIN")
        composable(
            route = SongListView.route,
            arguments = SongListView.arguments,
            deepLinks = SongListView.deepLinks
        ) {navBackStackEntry ->
            Log.d(TAG, "PlayMusicNavGraph -- composable $route")
            val tracklist = navBackStackEntry.arguments?.getString(SongListView.trackListArg)
//            val musicDB = MusicDB(LocalContext.current)
            TrackList(
                list = musicDB.songs,
                padding = PaddingValues(8.dp),
                onClickSong = { songID ->
                    navController.navigateToPlayingView(songID) }
            )
        }

        composable(
            route = AlbumListView.route,
            arguments = AlbumListView.arguments,
            deepLinks = AlbumListView.deepLinks
        ) { navBackStackEntry ->
            Log.d(TAG, "PlayMusicNavGraph -- composable $route")
//            val albumList = navBackStackEntry.arguments?.getString(AlbumListView.albumListArg)
//            val musicDB = MusicDB(LocalContext.current)
            AlbumCardGrid(
                list = musicDB.albums,
                paddingValues = PaddingValues(8.dp),
                onClickAlbum = { navController.navigateToTrackList(SongListView.route)}
            )
        }

        composable(
            route = ArtistListView.route,
            arguments = ArtistListView.arguments,
            deepLinks = ArtistListView.deepLinks
        ) { navBackStackEntry ->
            Log.d(TAG, "PlayMusicNavGraph -- composable $route")
//            val artistList = navBackStackEntry.arguments?.getString(ArtistListView.artistListArg)
//            val musicDB = MusicDB(LocalContext.current)
            ArtistsGridView(
                list = musicDB.artists,
                padding = PaddingValues(8.dp),
                onClickArtist = { navController.navigateToTrackList(SongListView.route) }
            )
        }

        composable(
            route = PlaylistView.route,
            arguments = PlaylistView.arguments,
            deepLinks = PlaylistView.deepLinks
        ) { navBackStackEntry ->
            Log.d(TAG, "PlayMusicNavGraph -- composable $route")
//            val playlist = navBackStackEntry.arguments?.getString(PlaylistView.playlistArg)
//            val musicDB = MusicDB(LocalContext.current)
            PlaylistCardList(
                list = listOf(),
                padding = PaddingValues(8.dp),
                onClickPlaylist = { navController.navigateToTrackList(SongListView.route)}
            )
        }

        composable(
            route = PlayingView.routeWithArgs,
            arguments = PlayingView.arguments,
            deepLinks = PlayingView.deepLinks
        ) { navBackStackEntry ->
            Log.d(TAG, "PlayMusicNavGraph -- composable $route")
            val songID = navBackStackEntry.arguments?.getLong(PlayingView.currentlyPlayingArg)
            val song = MusicDB.getTracks(LocalContext.current, songdId = songID)

            PlayingView(
                song = song[0],
                currentlyPlaying = currentlyPlayingState.currentlyPlaying)
        }
    }
    Log.d(TAG, "PlayMusicNavGraph -- composable Is it done?")
}

fun NavHostController.navigateSingleTopTo(route: String) {
    this.navigate(route) {
        popUpTo( this@navigateSingleTopTo.graph.findStartDestination().id ) {
            saveState = true

        }
        launchSingleTop = true
        restoreState = true
    }
}

private fun NavHostController.navigateToTrackList(tracklist: String) {
    this.navigateSingleTopTo("${SongListView.route}/$tracklist")
}

private fun NavHostController.navigateToAlbumList(albumList: String) {
    this.navigateSingleTopTo("${AlbumListView.route}/$albumList")
}

private  fun NavHostController.navigateToArtistList(artistList: String) {
    this.navigateSingleTopTo("${ArtistListView.route}/${artistList}")
}

private fun NavHostController.navigateToPlaylistView(playlist: String) {
    this.navigateSingleTopTo("${PlaylistView.route}/${playlist}")
}

private fun NavHostController.navigateToPlayingView(songID: Long) {
    this.navigateSingleTopTo("${PlayingView.route}/${songID}")
}