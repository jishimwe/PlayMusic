package com.ishim.playmusic.compose_layout

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ishim.playmusic.Playlist
import com.ishim.playmusic.R

@Composable
fun PlaylistCard(playlist: Playlist) {
    Card(
        modifier = Modifier.padding(6.dp).fillMaxWidth(),
        elevation = 4.dp,
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_album),
                contentDescription = "Icon")

            Text(
                text = playlist.name,
                style = MaterialTheme.typography.h5
            )

            Text(
                text = "23", // TODO: Hard coding is not ok... -> add playlist count
                style = MaterialTheme.typography.subtitle1
            )
        }
    }
}

@Composable
fun PlaylistCardList(list: List<Playlist>, padding: PaddingValues) {
    val listState = rememberLazyListState()
//    val songs = MusicDB.getTracks(context)
    LazyColumn(
        state = listState,
        contentPadding = padding
    ) {
        items(list) { playlist -> PlaylistCard(playlist = playlist) }
    }
}

@Preview
@Composable
fun PreviewPlaylist() {
    PlaylistCardList(list = DUMMY_PLAYLISTS, padding = PaddingValues(8.dp))
}