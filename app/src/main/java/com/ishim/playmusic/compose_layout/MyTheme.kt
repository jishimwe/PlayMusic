package com.ishim.playmusic.compose_layout

import android.content.Context
import android.content.res.Resources
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ishim.playmusic.MusicDB
import com.ishim.playmusic.R
import com.ishim.playmusic.Song

data class AppBarState(
    val title: String = "",
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
fun ScaffoldBars(songs: List<Song>) {
    val appBarState by remember { mutableStateOf(AppBarState()) }

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
                BottomPlayerBar()
            }
        }
    )
}

@Composable
//fun ScaffoldBarsGeneric(list: List<Any>, contentView: (input: List<Any>, padding: PaddingValues) -> Unit) {
fun ScaffoldBarsGeneric(context: Context, list: List<Any>, musicDB: MusicDB?) {
    val appBarState by remember { mutableStateOf(AppBarState()) }

    var tabState by rememberSaveable  { mutableStateOf(0) }

    Scaffold(
        topBar = {
            Column(
                Modifier
                    .background(MaterialTheme.colors.primary, RoundedCornerShape(24))
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
                MyTabsGeneric(tabState, onSelectedTab = {tabState = it})
            }
        },
        content = { innerPadding ->
//            To use with @Preview
/*            when(tabState) {
                0 -> TrackList(list = list.filterIsInstance<Song>(), padding = innerPadding)
                1 -> AlbumCardGrid(list = DUMMY_ALBUM_LIST, paddingValues = innerPadding)
                2 -> ArtistsGridView(list = DUMMY_ARTIST_LIST, padding = innerPadding)
                3 -> PlaylistCardList(list = DUMMY_PLAYLISTS, padding = innerPadding)
                else -> TrackList(list = list.filterIsInstance<Song>(), padding = innerPadding)
            }*/

//            To use with @MusicDB
            when(tabState) {
                1 -> if (musicDB != null) {
                    AlbumCardGrid(list = musicDB.albums, paddingValues = innerPadding)
                }
                2 -> if (musicDB != null) {
                    ArtistsGridView(list = musicDB.artists, padding = innerPadding)
                }
                3 -> PlaylistCardList(list = DUMMY_PLAYLISTS, padding = innerPadding)
                else -> if (musicDB != null) {
                    TrackList(list = musicDB.songs, padding = innerPadding)
                }
            }
        },
        bottomBar = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .wrapContentSize(Alignment.Center)
                    .fillMaxWidth()) {
                BottomPlayerBar()
            }
        }
    )
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
            .clip(RoundedCornerShape(48))
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

@Composable
fun BottomPlayerBar() {
    Surface(
        Modifier
            .clip(RoundedCornerShape(48))
            .height(54.dp)
            .fillMaxWidth(.95f)
            .background(MaterialTheme.colors.primary, RoundedCornerShape(24))
            .alpha(.6f),
        color = MaterialTheme.colors.secondary,
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
                Text(text = "Playing song")

                Text(text = "Artist")
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
                    painter = painterResource(id = R.drawable.play_arrow),
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
    MyTheme {
        Column(modifier = Modifier.padding(8.dp)) {
            ScaffoldBarsGeneric(LocalContext.current, songs, null)
    }

/*        val listState = rememberLazyListState()
        LazyColumn(
            state = listState
        ) {
            items(songs) { track -> SongCard(song = track) }
        }*/
    }
}