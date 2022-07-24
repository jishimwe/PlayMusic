package com.ishim.playmusic.compose_layout

import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ishim.playmusic.Album
import com.ishim.playmusic.R
import com.ishim.playmusic.Song

@Composable
fun AlbumCard(album: Album) {
    Card(shape = MaterialTheme.shapes.medium, modifier = Modifier.padding(8.dp)) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = R.drawable.ic_album),
                contentDescription = "Default album art",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxSize()
                    .aspectRatio(1f)
            )

            Text(
                text = album.name,
                style = MaterialTheme.typography.subtitle1,
                maxLines = 1,
                modifier = Modifier.padding(top = 4.dp, start = 4.dp, end = 4.dp)
            )

            Text(
                text = album.artist,
                style = MaterialTheme.typography.body1,
                maxLines = 1,
                modifier = Modifier.padding(bottom= 4.dp, start = 4.dp, end = 4.dp)
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AlbumCardGrid(list: List<Album>, paddingValues: PaddingValues) {
    LazyVerticalGrid(cells = GridCells.Fixed(2)) {
        items(list) { album ->
            AlbumCard(album)
        }
    }
}

@Preview
@Composable
fun MusicGridPreview() {
    val songs = mutableListOf<Song>()
    for (i in 0..12) {
        songs += Song(
            Uri.EMPTY,
            "Scream$i",
            "Dreamcatcher?",
            "${i}st Album?",
            "Dummy",
            180000,
            "Dummy data"
        )
    }
    AlbumCardGrid(list = DUMMY_ALBUM_LIST, PaddingValues(6.dp))
}