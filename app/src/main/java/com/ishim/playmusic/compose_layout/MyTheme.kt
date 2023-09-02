package com.ishim.playmusic.compose_layout

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.content.res.Resources.Theme
import android.net.Uri
import android.util.MutableInt
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.ishim.playmusic.CurrentlyPlaying
import com.ishim.playmusic.MusicDB
import com.ishim.playmusic.PlayingActivity
import com.ishim.playmusic.R
import com.ishim.playmusic.Song
import java.util.Locale

data class AppBarState(
    val artist: String,
    val title: String,
    val actions: (@Composable RowScope.() -> Unit)? = null
)

// Defining Colors
val darkSkyBlue = Color(0xff98c1d9)
val blueVioletCrayola = Color(0xFF6969B3)
val purpleMountain = Color(0xff9b82c4)
val palatinatePurple = Color(0xff4b244a)
val gunMetal = Color(0xff223843)

private val DarkColors = darkColors(
    primary = blueVioletCrayola,
    secondary = purpleMountain,
    secondaryVariant = darkSkyBlue
)

private val LightColors = lightColors(
    primary = palatinatePurple,
    secondary = gunMetal,
    secondaryVariant = darkSkyBlue
)

@Composable
fun MyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = if (darkTheme) DarkColors else LightColors,
        typography = MaterialTheme.typography,
        shapes = MaterialTheme.shapes,
        content = content
    )
}

/*@Composable
fun TopBar() {
    TopAppBar(
        title = Resources.getSystem().getString(R.string.app_name),
        backgroundColor = MaterialTheme.colors.background
    ) {

    }
}*/

@Composable
fun ScaffoldBars(songs: List<Song>, songPlaying: Song) {
    val appBarState by remember { mutableStateOf(AppBarState("artist", "title")) }

    Scaffold(
        topBar = {
            Column(
                Modifier
                    .background(MaterialTheme.colors.primary, RoundedCornerShape(24))
            ) {
                TopAppBar(
                    title = { Text(text = Resources.getSystem().getString(R.string.app_name)) },
                    actions = {
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(
                                imageVector = Icons.Outlined.PlayArrow,
                                contentDescription = "Icon"
                            )
                        }
                    })
                MyTabs()
            }
        },
        content = { innerPadding ->
            TrackList(list = songs, padding = innerPadding)
        },
        bottomBar = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .wrapContentSize(Alignment.Center)
                    .fillMaxWidth()) {
                BottomPlayerBar(songPlaying)
            }
        }
    )
}

@Composable
fun ScaffoldBarsGeneric(
    context: Context,
    list: List<Any>,
    musicDB: MusicDB?,
    songPlaying: Song?,
    allDestinations: List<PlayMusicDestinations>,
    currentScreen: PlayMusicDestinations,
    navController: NavHostController,
    onSelectedTab: (PlayMusicDestinations) -> Unit = {},
    currentlyPlayingState: CurrentlyPlayingState?,
) {
    val appBarState by remember {
        mutableStateOf(
            AppBarState(
                songPlaying?.artist ?: "artist",
                songPlaying?.name ?: "title")) }

    var tabState by rememberSaveable  { mutableStateOf(0) }

    Scaffold(
        topBar = {
            Column(
                Modifier
                    .background(MaterialTheme.colors.primary, RoundedCornerShape(6))
            ) {
                TopAppBar(
                    title = { Text(text = context.getString(R.string.app_name)) },
                    actions = {
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(
                                imageVector = Icons.Outlined.PlayArrow,
                                contentDescription = "Icon"
                            )
                        }
                    })
//                MyTabsGeneric(tabState, onSelectedTab = {tabState = it})
                Row {
                    allDestinations.forEach {destination ->
                        MyTabsGenericNav(
                            text = destination.route,
                            icon = destination.icon,
                            selected = destination == currentScreen,
                            selectedTab = allDestinations.indexOf(destination),
                            onSelectedTab = { onSelectedTab(destination) })
                    }
                }
            }
        },
        content = { innerPadding ->
            if (currentlyPlayingState != null && musicDB != null) {
                PlayMusicNavGraph(
                    navController = navController,
                    modifier = Modifier.padding(innerPadding),
                    currentlyPlayingState = currentlyPlayingState,
                    musicDB = musicDB)
            }
        },
        bottomBar =
        {
            if (tabState != 0) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .wrapContentSize(Alignment.Center)
                        .fillMaxWidth()
                ) {
                    BottomPlayerBar(songPlaying ?: DUMMY_SONG)
                }
            }
        }
    )
}

@Composable
fun MyTabsGenericNav(
    text: String,
    icon: ImageVector,
    selected: Boolean,
    selectedTab: Int,
    onSelectedTab: () -> Unit
) {
    val color = MaterialTheme.colors.onSurface
    val durationMillis = if (selected) TabFadeInAnimationDuration else TabFadeOutAnimationDuration
    val animSpec = remember {
        tween<Color>(
            durationMillis = durationMillis,
            easing = LinearEasing,
            delayMillis = TabFadeInAnimationDelay
        )
    }
    val tabTintColor by animateColorAsState(
        targetValue = if (selected) color else color.copy(alpha = InactiveTabOpacity),
        animationSpec = animSpec,
        label = ""
    )
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(4.dp)
            .animateContentSize()
            .height(TabHeight*.8F)
            .selectable(
                selected = selected,
                onClick = onSelectedTab,
                role = Role.Tab,
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(
                    bounded = false,
                    radius = Dp.Unspecified,
                    color = Color.Unspecified
                )
            )
            .clearAndSetSemantics { contentDescription = text }
            .border(1.dp, MaterialTheme.colors.secondaryVariant, CircleShape)
    ) {
        Icon(
            painter = painterResource(id = RouteIconsMap[text] ?: R.drawable.ic_tracks),
            contentDescription = text,
            tint = tabTintColor,
            modifier = Modifier
                .clip(CircleShape)
                .scale(0.7F)
                .padding(4.dp)
                .align(Alignment.CenterVertically))
        if (selected) {
            Divider(modifier = Modifier
                .width(2.dp)
                .fillMaxHeight(.8F))
            Text(
//                text.uppercase(Locale.getDefault()),
                text = text,
                color = tabTintColor,
                style = MaterialTheme.typography.h4,
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.CenterVertically)
            )
        }
    }
}

@Composable
fun MyTabs() {
    var selectedTab by remember {
        mutableStateOf(0)
    }
    val tabsName = listOf("Tracks", "Albums", "Artists", "Playlists")

    TabRow(
        selectedTabIndex = selectedTab,
        backgroundColor = MaterialTheme.colors.primary,
        modifier = Modifier
            .padding(vertical = 4.dp, horizontal = 4.dp)
            .clip(RoundedCornerShape(48))
            .padding(1.dp)
            .fillMaxWidth()
            .border(1.dp, MaterialTheme.colors.onPrimary, RoundedCornerShape(48)),
        indicator = { tabPositions: List<TabPosition> -> Box {} }
    ) {
        tabsName.forEachIndexed { index, text ->
            val selected = selectedTab == index
            Tab(
                modifier =
                if (selected) Modifier
                    .clip(RoundedCornerShape(48))
                    .background(MaterialTheme.colors.onPrimary)
                else Modifier
                    .clip(RoundedCornerShape(48))
                    .background(MaterialTheme.colors.primary),
                selected = selected,
                onClick = { selectedTab = index },
                text = {
                    Text(
                        text = text,
                        color =
                        if (selected)
                            MaterialTheme.colors.primary
                        else MaterialTheme.colors.onPrimary,
                        maxLines = 1
                    )
                }
            )
        }
    }
}

@Composable
fun MyTabsGeneric(selectedTab: Int, onSelectedTab: (Int) -> Unit) {
    val tabsName = listOf("Tracks", "Albums", "Artists", "Playlists")

    TabRow(
        selectedTabIndex = selectedTab,
        backgroundColor = MaterialTheme.colors.primary,
        modifier = Modifier
            .padding(vertical = 4.dp, horizontal = 4.dp)
            .clip(RoundedCornerShape(24))
            .padding(1.dp)
            .fillMaxWidth()
            .border(1.dp, MaterialTheme.colors.onPrimary, RoundedCornerShape(48)),
        indicator = { tabPositions: List<TabPosition> -> Box {} },
    ) {
        tabsName.forEachIndexed { index, text ->
            val selected = selectedTab == index
            Tab(
                modifier =
                if (selected) Modifier
                    .clip(RoundedCornerShape(48))
                    .background(MaterialTheme.colors.onPrimary)
                else Modifier
                    .clip(RoundedCornerShape(48))
                    .background(MaterialTheme.colors.primary),
                selected = selected,
                onClick = {onSelectedTab(index)},
                text = {
                    Text(
                        text = text,
                        color =
                        if (selected)
                            MaterialTheme.colors.primary
                        else MaterialTheme.colors.onPrimary,
                        maxLines = 1
                    )
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomPlayerBar(songPlaying: Song) {
    val playing by remember {
        mutableStateOf(true)
    }

    val context = LocalContext.current
    val intentPlayer = Intent(context, PlayingActivity::class.java)

    Surface(
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .clip(RoundedCornerShape(48))
            .height(54.dp)
            .fillMaxWidth(.95f)
            .background(MaterialTheme.colors.primary, RoundedCornerShape(24))
            .alpha(.8f)
            .clickable {
                intentPlayer.putExtra(SONG_ID, songPlaying.id)
                context.startActivity(intentPlayer)
            },
//        backgroundColor = MaterialTheme.colors.secondary,
//        onClick = { },
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_album),
                contentDescription = "Album art",
                modifier = Modifier
                    .offset(x = -(12.dp))
                    .weight(.2f)
                    .scale(.8f)
                    .aspectRatio(1f)
            )

            Column(modifier = Modifier.weight(.4f)) {
                Text(text = songPlaying.name ?: "Title")

                Text(text = songPlaying.artist ?: "Artist")
            }

            IconButton(onClick = { /*TODO*/ }, modifier = Modifier.weight(.1f)) {
                Icon(
                    painter = painterResource(id = R.drawable.skip_previous),
                    contentDescription = "Previous",
                    modifier = Modifier
                        .clip(CircleShape)
                        .scale(.7f)
                )
            }

            IconButton(onClick = { /*TODO*/ }, modifier = Modifier.weight(.1f)) {
                Icon(
                    painter = if (playing)
                        painterResource(id = R.drawable.play_arrow)
                    else
                        painterResource(id = R.drawable.pause),
                    contentDescription = "Play",
                    modifier = Modifier
                        .clip(CircleShape)
                        .border(1.dp, MaterialTheme.colors.secondaryVariant, CircleShape)
                )
            }

            IconButton(onClick = { /*TODO*/ }, modifier = Modifier.weight(.1f)) {
                Icon(
                    painter = painterResource(id = R.drawable.skip_next),
                    contentDescription = "Next",
                    modifier = Modifier
                        .clip(CircleShape)
                        .scale(.7f)
                )
            }
        }
    }

}

private val TabHeight = 56.dp
private const val InactiveTabOpacity = 0.60f

private const val TabFadeInAnimationDuration = 150
private const val TabFadeInAnimationDelay = 100
private const val TabFadeOutAnimationDuration = 100
private val RouteIconsMap = mapOf<String, Int>(
    SongListView.route to R.drawable.ic_tracks,
    AlbumListView.route to R.drawable.ic_album,
    ArtistListView.route to R.drawable.ic_artist,
    PlaylistView.route to R.drawable.ic_playlist
)

@Composable
@Preview
fun PreviewTabs() {
    MyTheme {
        MyTabs()
    }
}

/*
@Composable
@Preview
fun PreviewSongCard() {
    val song = Song(
        Uri.EMPTY,
        "Scream?",
        "Dreamcatcher?",
        "1st Album?",
        "Dummy",
        180000,
        "Dummy data"
    )
    SongCard(song)
}*/

@Composable
@Preview
fun PreviewSongList() {
    val songs = mutableListOf<Song>()
    for (i in 0..12) {
        songs += Song(
            i.toLong(),
            Uri.EMPTY,
            "Scream$i",
            "Dreamcatcher?",
            "${i}st Album?",
            "Dummy",
            180000,
            "Dummy data"
        )
    }
    val navController = rememberNavController()
    MyTheme {
        Column(modifier = Modifier.padding(8.dp)) {
            ScaffoldBarsGeneric(
                LocalContext.current,
                songs,
                null,
                songs[0],
                playMusicTabs,
                playMusicTabs[0],
                navController,
                currentlyPlayingState = null)
    }

/*        val listState = rememberLazyListState()
        LazyColumn(
            state = listState
        ) {
            items(songs) { track -> SongCard(song = track) }
        }*/
    }
}