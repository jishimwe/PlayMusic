package com.ishim.playmusic.compose_layout

import android.net.Uri
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ishim.playmusic.CurrentlyPlaying
import com.ishim.playmusic.R
import com.ishim.playmusic.Song
import com.ishim.playmusic.timeFormat
import java.time.Instant
import java.time.LocalTime
import java.time.ZoneId

@Composable
fun AlbumArtAndInfo(song: Song, viewModel: PlayMusicViewModel = viewModel()) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()
//    val album = MusicDB.getAlbums(context, song.album)
//    val albumArt = MusicDB.getAlbumArt(context, album[0].id)

    Card(
        shape = MaterialTheme.shapes.large,
        backgroundColor = MaterialTheme.colors.surface,
        modifier = Modifier
            .scale(.95f)
            .clip(RoundedCornerShape(24.dp))
            .animateContentSize()
    ) {
        Column(modifier = Modifier
            .clickable {}
            .aspectRatio(.8f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(
                    id = R.drawable.ic_album
                ),
                contentDescription = "Album default image",
                modifier = Modifier
                    .aspectRatio(1f)
                    .border(1.dp, MaterialTheme.colors.primary, RoundedCornerShape(24.dp))
                    .clip(RoundedCornerShape(24.dp))
                    .shadow(2.dp)
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth()
            )

            Text(
//                text = song.name,
                text = uiState.song?.name ?: "VIEW MODEL FAIL: title",
                style = MaterialTheme.typography.h6
            )

            Text(
//                text = song.artist,
                text = uiState.song?.artist ?: "VIEW MODEL FAIL: artist",
                style = MaterialTheme.typography.body1,
                maxLines = 1,
                modifier = Modifier
                    .padding(4.dp)
            )

            Spacer(modifier = Modifier.width(4.dp))

            Text(
//                text = song.album,
                text = uiState.song?.album ?: "VIEW MODEL FAIL: album",
                style = MaterialTheme.typography.body1,
                maxLines = 1,
                modifier = Modifier
                    .padding(4.dp)
            )
        }

    }
}

@Composable
fun MediaButtonAndProgressBar(
    song: Song,
    currentlyPlaying: CurrentlyPlaying?,
    viewModel: PlayMusicViewModel = viewModel()
) {
    val timeElapsed by remember {
        mutableStateOf(0)
    }
    val instantElapsed = Instant.ofEpochMilli(timeElapsed.toLong())
    val durElapsed = LocalTime.ofInstant(instantElapsed, ZoneId.systemDefault())
    val instant = Instant.ofEpochMilli(song.duration)
    val dur = LocalTime.ofInstant(instant, ZoneId.systemDefault())

    val context = LocalContext.current

    currentlyPlaying?.playSong(song, context)
    val uiState by viewModel.uiState.collectAsState()

    var playing by remember {
        mutableStateOf(true)
    }

    Card(
        shape = MaterialTheme.shapes.medium,
        backgroundColor = MaterialTheme.colors.surface,
        modifier = Modifier
            .scale(.95f)
            .clip(RoundedCornerShape(24.dp))
            .animateContentSize()
            .fillMaxHeight(.5f)
//            .wrapContentSize()
    ) {
        Column(
            modifier = Modifier
                .padding(4.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            var sliderValue by remember { mutableStateOf(0f) }

            Row(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth(2f)
                    .weight(.3f)
                    .padding(end = 8.dp, start = 8.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = timeFormat.format(durElapsed),
                    style = MaterialTheme.typography.body2,
                    maxLines = 1,
                    color = MaterialTheme.colors.onSecondary,
                    modifier = Modifier.weight(.15f)
                )

                Slider(
                    value = sliderValue,
                    onValueChange = { newValue -> sliderValue = newValue },
                    colors = SliderDefaults.colors(),
                    modifier = Modifier.weight(.8f)
                )

                Text(
                    text = timeFormat.format(dur),
                    style = MaterialTheme.typography.body2,
                    maxLines = 1,
                    color = MaterialTheme.colors.onSecondary,
                    modifier = Modifier.weight(.15f)
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Row(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .weight(.7f)
                    .padding(start = 8.dp, end = 8.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.repeat),
                        contentDescription = "Repeat",
                        Modifier
                            .scale(.5f)
//                            .padding(end = 32.dp)
                    )
                }

                Spacer(modifier = Modifier.width(48.dp))

                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.skip_previous),
                        contentDescription = "Previous",
//                        modifier = Modifier
//                            .clip(RoundedCornerShape(.5.dp))
//                            .padding(start = 8.dp, end = 8.dp)
                    )
                }

//                Spacer(modifier = Modifier.width(16.dp))

                IconButton(onClick = {
                    playing = if (playing) {
                        currentlyPlaying?.pauseSong(song, context)
                        false
                    } else {
                        currentlyPlaying?.playSong(song, context)
                        true
                    }
                }) {
                    Icon(
                        imageVector = if (!playing) {
                            ImageVector.vectorResource(id = R.drawable.play_arrow)
                        } else {
                            ImageVector.vectorResource(id = R.drawable.pause) },
                        contentDescription = "Play",
                        modifier = Modifier
                            .scale(1.25f)
//                            .background(MaterialTheme.colors.surface)
                            .border(.5.dp, MaterialTheme.colors.primary, CircleShape)
                            .clip(CircleShape)
//                            .padding(start = 8.dp, end = 8.dp)
                    )
                }

//                Spacer(modifier = Modifier.width(16.dp))

                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.skip_next),
                        contentDescription = "Next",
//                        modifier = Modifier
//                            .clip(RoundedCornerShape(.5.dp))
//                            .padding(start = 8.dp, end = 8.dp)
                    )
                }

                Spacer(modifier = Modifier.width(48.dp))

                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.shuffle),
                        contentDescription = "Shuffle",
                        Modifier
                            .scale(.5f)
//                            .padding(start = 32.dp)
                    )
                }
            }
        }

    }
}

@Composable
fun PlayingView(song: Song, currentlyPlaying: CurrentlyPlaying?, viewModel: PlayMusicViewModel) {
        Column(
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            AlbumArtAndInfo(song, viewModel)

            Spacer(modifier = Modifier.width(18.dp))

            MediaButtonAndProgressBar(song, currentlyPlaying, viewModel)
        }
}

@Preview
@Composable
fun AlbumArtAndInfoPreview() {
    val song = Song(
        123456789,
        Uri.EMPTY,
        "Scream?",
        "Dreamcatcher?",
        "1st Album?",
        "Dummy",
        180000,
        "Dummy data"
    )

//    val currentlyPlaying = CurrentlyPlaying(LocalContext.current, MediaPlayer(), null, song.id)
//    val viewModel: PlayMusicViewModel by viewModels()
    PlayingView(song = song, null, PlayMusicViewModel())
}