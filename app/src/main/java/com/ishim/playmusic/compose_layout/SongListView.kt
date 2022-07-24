package com.ishim.playmusic.compose_layout

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ishim.playmusic.R
import com.ishim.playmusic.Song

@Composable
fun SongCard(song: Song) {
    Surface(
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colors.surface,
        elevation = 2.dp,
        modifier = Modifier
            .animateContentSize()
            .clip(RoundedCornerShape(2.dp))
    ) {

        Row(modifier = Modifier.padding(all = 8.dp)) {
            Image(
                painter = painterResource(id = R.drawable.ic_album),
                contentDescription = "Album default image",
                modifier = Modifier
//                    .size(54.dp, 54.dp)
//                    .clip(CircleShape)
                    .aspectRatio(1f)
                    .weight(.2f)
                    .border(1.dp, MaterialTheme.colors.secondary, RoundedCornerShape(8.dp))
                    .align(Alignment.CenterVertically)
            )

            Column(modifier = Modifier
                .clickable { }
                .padding(4.dp)
                .weight(.8f)) {
                Text(
                    text = song.name,
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier.padding(4.dp)
                )

                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = song.artist,
                        style = MaterialTheme.typography.body1,
                        maxLines = 1,
                        modifier = Modifier
                            .padding(4.dp)
                            .weight(.5f)
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = song.album,
                        style = MaterialTheme.typography.body1,
                        maxLines = 1,
                        modifier = Modifier
                            .padding(4.dp)
                            .weight(.5f)
                    )
                }

            }
        }
    }
}

@Composable
fun TrackList(list: List<Song>, padding: PaddingValues) {
    val listState = rememberLazyListState()
//    val songs = MusicDB.getTracks(context)
    LazyColumn(
        state = listState,
        contentPadding = padding
    ) {
        items(list) { track -> SongCard(song = track) }
    }
}