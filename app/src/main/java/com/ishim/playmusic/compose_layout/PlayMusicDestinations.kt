package com.ishim.playmusic.compose_layout

import android.widget.ImageView
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink

interface PlayMusicDestinations{
    val icon: ImageVector
    val route: String
//    val screen: @Composable () -> Unit
}

object SongListView: PlayMusicDestinations {
    override val icon = Icons.Default.List
    override val route = NavDestinations.SONG_LIST_VIEW
//    override val screen: @Composable () -> Unit = {  }

    const val trackListArg = "tracklist"
    val routeWithArgs = "${route}/{${trackListArg}}"
    val arguments = listOf(navArgument(trackListArg) { type = NavType.StringType; nullable = true })
    val deepLinks = listOf(navDeepLink { uriPattern = "play_music://$route/{$trackListArg}" })
}

object AlbumListView: PlayMusicDestinations {
    override val icon = Icons.Default.CheckCircle
    override val route = NavDestinations.ALBUM_LIST_VIEW
//    override val screen: @Composable () -> Unit = {  }

    const val albumListArg = "albumList"
    val routeWithArgs = "${route}/{${albumListArg}}"
    val arguments = listOf(navArgument(albumListArg) { type = NavType.StringType; nullable = true })
    val deepLinks = listOf(navDeepLink { uriPattern = "play_music://$route/{$albumListArg}" })
}

object ArtistListView: PlayMusicDestinations {
    override val icon = Icons.Default.Person
    override val route = NavDestinations.ARTIST_LIST_VIEW
//    override val screen: @Composable () -> Unit = {  }

    const val artistListArg = "artistList"
    val routeWithArgs = "${route}/{${artistListArg}}"
    val arguments = listOf(navArgument(artistListArg) { type = NavType.StringType; nullable = true })
    val deepLinks = listOf(navDeepLink { uriPattern = "play_music://$route/{$artistListArg}" })
}

object PlaylistView: PlayMusicDestinations {
    override val icon = Icons.Default.List
    override val route = NavDestinations.PLAYLIST_LIST_VIEW
//    override val screen: @Composable () -> Unit = {  }

    const val playlistArg = "playlist"
    val routeWithArgs = "${route}/{${playlistArg}}"
    val arguments = listOf(navArgument(playlistArg) { type = NavType.StringType; nullable = true })
    val deepLinks = listOf(navDeepLink { uriPattern = "play_music://$route/{$playlistArg}" })
}

object PlayingView: PlayMusicDestinations {
    override val icon = Icons.Default.LocationOn
    override val route = NavDestinations.CURRENTLY_PLAYING_VIEW
//    override val screen: @Composable () -> Unit = {  }

    const val currentlyPlayingArg = "currentlyPlaying"
    val routeWithArgs = "${route}/{${currentlyPlayingArg}}"
    val arguments = listOf(navArgument(currentlyPlayingArg) { type = NavType.LongType })
    val deepLinks = listOf(navDeepLink { uriPattern =
        "play_music://$route/{$currentlyPlayingArg}" })
}

val playMusicTabs = listOf(SongListView, AlbumListView, ArtistListView, PlaylistView)