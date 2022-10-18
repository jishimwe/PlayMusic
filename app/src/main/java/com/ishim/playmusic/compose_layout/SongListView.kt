package com.ishim.playmusic.compose_layout

import android.content.Intent
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ishim.playmusic.PlayingActivity
import com.ishim.playmusic.R
import com.ishim.playmusic.Song
import com.ishim.playmusic.timeFormat
import java.time.Instant
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

const val SONG_ID = "SongID"

@Composable
fun SongCard(song: Song) {
    val context = LocalContext.current
    val intentPlayer = Intent(context, PlayingActivity::class.java)

    val instant = Instant.ofEpochMilli(song.duration)
    val dur = LocalTime.ofInstant(instant, ZoneId.systemDefault())

    Card(
        shape = MaterialTheme.shapes.small,
//        color = MaterialTheme.colors.surface,
        elevation = 8.dp,
        modifier = Modifier
            .clickable {
                intentPlayer.putExtra(SONG_ID, song.id)
                context.startActivity(intentPlayer)
            }
            .animateContentSize()
            .clip(RoundedCornerShape(8.dp))
            .border(1.dp, MaterialTheme.colors.primary, RoundedCornerShape(8.dp))
            .wrapContentHeight()
    ) {

        Row(modifier = Modifier
            .padding(start = 4.dp, end = 4.dp)
            .fillMaxHeight(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_album),
                contentDescription = "Album default image",
                modifier = Modifier
//                    .size(54.dp, 54.dp)
//                    .clip(CircleShape)
                    .aspectRatio(1f)
                    .weight(.1f)
                    .border(1.dp, MaterialTheme.colors.secondary, RoundedCornerShape(8.dp))
                    .align(Alignment.CenterVertically)
            )

            Column(modifier = Modifier
                .padding(4.dp)
                .weight(.8f)) {
                Text(
                    text = song.name,
                    style = MaterialTheme.typography.subtitle2,
                    modifier = Modifier.padding(4.dp)
                )

                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(
                        text = song.artist,
                        style = MaterialTheme.typography.caption,
                        maxLines = 1,
                        modifier = Modifier
                            .padding(2.dp)
                            .weight(.5f)
                    )

                    Text(
                        text = "•",
                        style = MaterialTheme.typography.caption,
                        maxLines = 1,
                        modifier = Modifier
                            .padding(4.dp)
                            .weight(.1f)
                    )

                    Text(
                        text = song.album,
                        style = MaterialTheme.typography.caption,
                        maxLines = 1,
                        modifier = Modifier
                            .padding(2.dp)
                            .weight(.5f)
                    )

                    Text(
                        text = "•",
                        style = MaterialTheme.typography.caption,
                        maxLines = 1,
                        modifier = Modifier
                            .padding(4.dp)
                            .weight(.1f)
                    )

                    Text(
//                        text = "${song.duration}",
                        text = timeFormat.format(dur),
                        style = MaterialTheme.typography.caption,
                        maxLines = 1,
                        modifier = Modifier
                            .padding(2.dp)
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
        contentPadding = padding,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(list) { track -> SongCard(song = track) }
    }
}

@Preview
@Composable
fun TrackListPreview() {
    MyTheme {
        TrackList(list = DUMMY_SONG_LIST, padding = PaddingValues(12.dp))
    }
}